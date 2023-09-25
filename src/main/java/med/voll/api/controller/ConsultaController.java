package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Consultas y citas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;
    @Autowired
    private ConsultaRepository repository;
    @PostMapping("/agendar")
    @Operation(
            summary = "Agendar una consulta",
            description = "Agenda una nueva consulta con la información proporcionada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta agendada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Datos inválidos)")
    })
    @Transactional
    public ResponseEntity agendarCita(@RequestBody @Valid DatosAgendarConsulta datos){
        var response = service.agendar(datos);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/cancelar")
    @Operation(
            summary = "Cancelar una consulta",
            description = "Cancela una consulta existente basado en los datos proporcionados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta cancelada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Datos inválidos)")
    })
    @Transactional
    public ResponseEntity cancelarConsulta(@RequestBody @Valid DatosCancelarConsulta datos){
        DatosDetalleConsulta consultaCancelada = service.cancelarConsulta(datos);
        return ResponseEntity.ok(consultaCancelada);
    }

    @GetMapping("/{idConsulta}")
    @Operation(
            summary = "Obtener información de una consulta",
            description = "Recibe información sobre una consulta específica basada en su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información de la consulta obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Parámetros inválidos)")
    })
    public ResponseEntity<DatosDetalleConsulta> obtenerConsulta(@PathVariable Long idConsulta){
        Consulta consulta =repository.getReferenceById(idConsulta);
        return ResponseEntity.ok(new DatosDetalleConsulta(consulta));
    }

}
