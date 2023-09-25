package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosCancelarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;
    @PostMapping("/agendar")
    @Transactional
    public ResponseEntity agendarCita(@RequestBody @Valid DatosAgendarConsulta datos){
        var response = service.agendar(datos);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/cancelar")
    @Transactional
    public ResponseEntity cancelarConsulta(@RequestBody @Valid DatosCancelarConsulta datos){
        service.cancelarConsulta(datos);
        return ResponseEntity.noContent().build();
    }

}
