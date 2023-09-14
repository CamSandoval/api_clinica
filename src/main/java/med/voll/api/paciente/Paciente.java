package med.voll.api.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.direccion.Direccion;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String documento;
    private String telefono;

    @Embedded
    private Direccion direccion;

    private boolean activo;

    public Paciente(DatosRegistroPaciente datos) {
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.documento = datos.documento();
        this.direccion = new Direccion(datos.direccion());
        this.activo= true;
    }

    public Paciente actualizarDatos(DatosActualizarPaciente datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.telefono() != null) {
            this.telefono = datos.telefono();
        }
        if (datos.documento() != null) {
            this.documento = datos.documento();
        }
        if (datos.direccion() != null) {
            direccion.actualizarDatos(datos.direccion());
        }
        return this;
    }

    public void desactivarPaciente(Paciente paciente) {
        this.activo = false;
    }

    @Override
    public String toString() {
        return String.format("{" +
                "\"id\" : \"%s\"," +
                "\"nombre\" : \"%s\"," +
                "\"email\" : \"%s\"," +
                "\"documento\" : \"%s\"" +
                "}",this.id,this.nombre,this.email,this.documento);
    }
}
