package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping("/registrar")
    @Operation(
            summary = "Registrar un nuevo paciente",
            description = "Este endpoint te permite registrar un nuevo paciente con la información necesaria"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Parámetros inválidos)")
    })
    @Transactional
    public ResponseEntity<DatosListadoPaciente> registrarPaciente(@Valid @RequestBody DatosRegistroPaciente datosPaciente, UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteRepository.save(new Paciente(datosPaciente));
        DatosListadoPaciente datosListadoPaciente = new DatosListadoPaciente(paciente);
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosListadoPaciente);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Obtener una lista de pacientes activos",
            description = "Recibe una lista paginada de pacientes activos desde la base de datos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Parámetros inválidos)")
    })
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(Pageable pageable){
        Page<DatosListadoPaciente> pacientes = pacienteRepository.findByActivoTrue(pageable).map(DatosListadoPaciente::new);
        return ResponseEntity.ok(pacientes);
    }

    @PutMapping("/update")
    @Operation(
            summary = "Actualizar información de un paciente",
            description = "Actualiza la información de un paciente existente basado en los datos proporcionados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información del paciente actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Datos inválidos)")
    })
    @Transactional
    public ResponseEntity<DatosListadoPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosListadoPaciente(paciente));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Desactivar un paciente",
            description = "Desactiva un paciente estableciéndolo como inactivo en la base de datos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente desactivado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Parámetros inválidos)")
    })
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente(paciente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener información de un paciente",
            description = "Recibe información sobre un paciente específico basado en su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información del paciente obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Parámetros inválidos)")
    })
    public ResponseEntity<DatosListadoPaciente> obtenerPaciente(@PathVariable Long id){
        DatosListadoPaciente datosPaciente = new DatosListadoPaciente(pacienteRepository.getReferenceById(id));
        return ResponseEntity.ok(datosPaciente);
    }

}
