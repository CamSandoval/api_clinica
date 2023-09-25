package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCancelarConsulta(@NotNull Long idConsulta,@NotNull(message = "Por favor seleccione un motivo de cancelación")@JsonAlias("motivo") MotivoDeCancelacion motivoDeCancelacion) {
}
