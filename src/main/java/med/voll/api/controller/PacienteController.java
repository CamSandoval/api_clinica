package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<DatosListadoPaciente> registrarPaciente(@Valid @RequestBody DatosRegistroPaciente datosPaciente, UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteRepository.save(new Paciente(datosPaciente));
        DatosListadoPaciente datosListadoPaciente = new DatosListadoPaciente(paciente);
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosListadoPaciente);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(Pageable pageable){
        Page<DatosListadoPaciente> pacientes = pacienteRepository.findByActivoTrue(pageable).map(DatosListadoPaciente::new);
        return ResponseEntity.ok(pacientes);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<DatosListadoPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosListadoPaciente(paciente));
    }

    @DeleteMapping("/delete{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente(paciente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoPaciente> obtenerPaciente(@PathVariable Long id){
        DatosListadoPaciente datosPaciente = new DatosListadoPaciente(pacienteRepository.getReferenceById(id));
        return ResponseEntity.ok(datosPaciente);
    }

}
