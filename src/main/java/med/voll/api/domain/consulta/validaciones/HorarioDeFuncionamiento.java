package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;

import java.time.DayOfWeek;

public class HorarioDeFuncionamiento {
    public void validar(DatosAgendarConsulta datos){
        boolean es_domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
        boolean antesDeHoraDeApertura = datos.fecha().getHour() <7;
        boolean despuesDeHoraDeApertura = datos.fecha().getHour() > 19;
        if(es_domingo || antesDeHoraDeApertura || despuesDeHoraDeApertura){
            throw new ValidationException("El horario de atención de la clinica es de lunes a sabado de 7:00 a 19:00 horas");
        }
    }
}
