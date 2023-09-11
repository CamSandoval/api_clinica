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
    @Column(name = "documento")
    private String documentoIdentidad;
    private String telefono;

    @Embedded
    private Direccion direccion;

    private boolean activo;

    public Paciente(DatosRegistroPaciente datos) {
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.documentoIdentidad = datos.documento();
        this.direccion = new Direccion(datos.direccion());
    }

    public Paciente actualizarDatos(DatosActualizarPaciente datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.telefono() != null) {
            this.telefono = datos.telefono();
        }
        if (datos.direccion() != null) {
            direccion.actualizarDatos(datos.direccion());
        }
        return this;
    }

    public void desactivarPaciente(Paciente paciente) {
        this.activo = false;
    }
}
