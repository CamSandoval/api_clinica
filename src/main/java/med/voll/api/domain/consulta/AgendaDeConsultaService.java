package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.consulta.validaciones.cancelaciones.ValidacionDeCancelaciones;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private List<ValidadorDeConsultas> validacionesAgendar;
    @Autowired
    private List<ValidacionDeCancelaciones> validacionesCancelaciones;
    /**
     * @ValidacionDeIntegirdad valida que los datos enviados esten correctamente diligenciados
     * @param datosAgendarConsulta datos provenientes del cliente para la petición
     * */
    public DatosDetalleConsulta agendar(DatosAgendarConsulta datosAgendarConsulta){

        if(!(pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent())){
            throw new ValidacionDeIntegridad("El id del paciente no fue encontrado");
        }

        if(datosAgendarConsulta.idMedico()!=null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }
        validacionesAgendar.forEach(val->val.validar(datosAgendarConsulta));
        Paciente paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();
        Medico medico = seleccionarMedico(datosAgendarConsulta);

        if(medico==null){
            throw new ValidacionDeIntegridad("No existen medicos disponibles para este horario y especialidad");
        }
        Consulta consulta = new Consulta(null,medico,paciente,datosAgendarConsulta.fecha(),true);
        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta);
    }

    /**
     * @return este metodo retorna un medico de la especialidad y fecha de consulta solicitada
     * */
    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.idMedico()!=null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad()==null){
            throw new ValidacionDeIntegridad("Debe seleccionar una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadYFechaDisponible(datosAgendarConsulta.especialidad(),datosAgendarConsulta.fecha());
    }

    public DatosDetalleConsulta cancelarConsulta(DatosCancelarConsulta datos) {
        if(!consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionDeIntegridad("La cita que desea cancelar no existe");
        }
        Consulta consulta =consultaRepository.getReferenceById(datos.idConsulta());
        validacionesCancelaciones.forEach(valid->valid.validar(datos));
        consulta.cancelar();
        return new DatosDetalleConsulta(consulta);
    }
}
