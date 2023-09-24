package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    public void agendar(DatosAgendarConsulta datosAgendarConsulta){

        if(!(pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent())){
            throw new ValidacionDeIntegridad("El id del paciente no fue encontrado");
        }

        if(!medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionDeIntegridad("El id del medico no fue encontrado");
        }
        if(datosAgendarConsulta.idMedico()!=null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }
        Paciente paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();
        Medico medico = seleccionarMedico(datosAgendarConsulta);
        Consulta consulta = new Consulta(null,medico,paciente,datosAgendarConsulta.fecha());
        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.idMedico()!=null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad()==null){
            throw new ValidacionDeIntegridad("Debe seleccionar una especialidad para el medico");
        }
        System.out.println("aqui estoyyyyy");
        return medicoRepository.selecccionarSegundoMedico(datosAgendarConsulta.especialidad(),datosAgendarConsulta.fecha());
    }
}
