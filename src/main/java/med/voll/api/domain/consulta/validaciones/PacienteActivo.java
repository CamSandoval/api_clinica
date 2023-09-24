package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

public class PacienteActivo {

    private PacienteRepository pacienteRepository;
    public void validar(DatosAgendarConsulta datos){
        if(datos.idPaciente()==null){
            return;
        }
        var pacienteActivo = pacienteRepository.findActivoById(datos.idPaciente());

        if(!pacienteActivo){
            throw new ValidationException("No se puede permitir agendar pacientes inactivos en el sistema");
        }
    }
}
