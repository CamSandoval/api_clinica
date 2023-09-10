package med.voll.api.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.direccion.Direccion;

@Entity
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

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.nombre= datosRegistroMedico.nombre();
        this.email= datosRegistroMedico.email();
        this.telefono=datosRegistroMedico.telefono();
        this.documento= datosRegistroMedico.documento();
        this.especialidad= datosRegistroMedico.especialidad();
        this.direccion= new Direccion(datosRegistroMedico.direccion());
    }
}
