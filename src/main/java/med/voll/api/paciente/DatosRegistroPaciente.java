package med.voll.api.paciente;

import med.voll.api.direccion.DatosDireccion;

public record DatosPaciente(String nombre, String email, String telefono, String documentoIdentidad, DatosDireccion direccion) {
}
