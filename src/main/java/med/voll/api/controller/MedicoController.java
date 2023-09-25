package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.DatosListadoMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;
    @PostMapping("/registrar")
    @Operation(
            summary = "Registrar un nuevo médico",
            description = "Registra un nuevo médico con la información proporcionada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Médico registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Datos inválidos)")
    })
    @Transactional
    public ResponseEntity<DatosListadoMedico> registrarMedico(@Valid @RequestBody DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosListadoMedico datosListadoMedico = new DatosListadoMedico(medico);
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosListadoMedico);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Obtener una lista de médicos",
            description = "Recibe una lista paginada de médicos activos desde la base de datos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de médicos obtenida exitosamente"),
            @ApiResponse(responseCode =  "400", description = "Solicitud incorrecta (Parámetros inválidos)")
    })
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 5) Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new)) ;
    }

    @PutMapping("/update")
    @Operation(
            summary = "Actualizar información de un médico",
            description = "Actualiza la información de un médico existente basado en los datos proporcionados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información del médico actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Datos inválidos)")
    })
    @Transactional
    public ResponseEntity<DatosListadoMedico> actualizarMedico(@Valid @RequestBody DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosListadoMedico(medico));
    }

    @DeleteMapping("/delete{id}")
    @Operation(
            summary = "Desactivar un médico",
            description = "Desactiva un médico estableciéndolo como inactivo en la base de datos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Médico desactivado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Parámetros inválidos)")
    })
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener información de un médico",
            description = "Recibe información sobre un médico específico basado en su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información del médico obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta (Parámetros inválidos)")
    })
    public ResponseEntity<DatosListadoMedico> obtenerMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosListadoMedico(medico));
    }
}
