package med.voll.api.domain.consulta.validaciones.cancelaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioParaCancelar implements ValidacionDeCancelaciones{
    @Autowired
    private ConsultaRepository repository;
    @Override
    public void validar(DatosCancelarConsulta datos) {
        var ahora = LocalDateTime.now();
        Consulta consulta = repository.getReferenceById(datos.idConsulta());
        var horaDeConsulta = consulta.getData();

        boolean laCancelacionTiene24HorasDeAnticipacion = Duration.between(ahora,horaDeConsulta).toHours()<24;
        if (laCancelacionTiene24HorasDeAnticipacion){
            throw new ValidationException("Las consultas no se pueden cancelar con menos de 24 horas de anticipacion");
        }
    }
}
