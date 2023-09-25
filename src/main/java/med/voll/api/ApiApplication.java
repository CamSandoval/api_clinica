package med.voll.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API diseñada para la clinica vollmed",
				version = "1.0",
				description = """
						Esta es un API creada para permitir el agendamiento de consultas, registro de medicos y pacientes  \s
						\s
						(By: CamSandoval)\s"""
		)
)
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
