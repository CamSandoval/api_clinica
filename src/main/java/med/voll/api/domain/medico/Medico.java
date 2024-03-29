package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Entity(name = "Medico")
@Table(name = "medicos")
//funcionalidades de lombok
    //Genera en tiempo de compilacion todos los getters
@Getter
    //Genera un contructor sin argumentos
@NoArgsConstructor
    //Genera un constructor con todos los argumentos
@AllArgsConstructor
    //Sobre escribe el metodo hashcode con la llave que le asignemos
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    private boolean activo;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.nombre= datosRegistroMedico.nombre();
        this.email= datosRegistroMedico.email();
        this.telefono=datosRegistroMedico.telefono();
        this.documento= datosRegistroMedico.documento();
        this.especialidad= datosRegistroMedico.especialidad();
        this.activo= true;
        this.direccion= new Direccion(datosRegistroMedico.direccion());
    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico){
        if(datosActualizarMedico.nombre() != null) {
            this.nombre=datosActualizarMedico.nombre();
        }
        if(datosActualizarMedico.documento() != null) {
            this.documento=datosActualizarMedico.documento();
        }
        if(datosActualizarMedico.direccion() != null) {
            this.direccion= direccion.actualizarDatos(datosActualizarMedico.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo=false;
    }
}
