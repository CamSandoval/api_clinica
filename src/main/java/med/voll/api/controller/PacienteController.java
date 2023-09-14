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

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<Paciente> registrarPaciente(@Valid @RequestBody DatosRegistroPaciente datosPaciente){
        Paciente paciente = pacienteRepository.save(new Paciente(datosPaciente));
        return new ResponseEntity<>(paciente,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(Pageable pageable){
        Page<DatosListadoPaciente> pacientes = pacienteRepository.findByActivoTrue(pageable).map(DatosListadoPaciente::new);
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<String> actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok().body(paciente.toString());
    }

    @DeleteMapping("/delete{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente(paciente);
        return ResponseEntity.noContent().build();
    }

}
