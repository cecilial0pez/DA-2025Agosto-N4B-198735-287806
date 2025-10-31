package da.obligatorio.peajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import da.obligatorio.peajes.modelo.DatosPrueba;

@SpringBootApplication
public class PeajesApplication {

	public static void main(String[] args) {
		DatosPrueba.cargar(); // <<-- importante
		SpringApplication.run(PeajesApplication.class, args);
	}

}
