package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class HorarioDeAnticipacion {
    public void validar(DatosAgendarConsulta datos){
        var ahora = LocalDateTime.now();
        var horaDeConsulta = datos.fecha();

        var diferenciaDe30Minutos = Duration.between(ahora,horaDeConsulta).toMinutes()<30;
        if(diferenciaDe30Minutos){
            throw new ValidationException("Las consutas se deben programar con almenos 30 minutos de anticipación");
        }
    }
}
