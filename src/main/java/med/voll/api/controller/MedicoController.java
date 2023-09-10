package med.voll.api.controller;

import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.DatosRegistroMedico;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @PostMapping
    public void registrarMedico(@RequestBody DatosRegistroMedico datosRegistroMedico){
        System.out.println(datosRegistroMedico);
    }
    static DatosDireccion datosRegistroMedico = new DatosDireccion("1","2","city","5","es");

    public static void main(String[] args) {
        System.out.println(datosRegistroMedico);
    }
}
