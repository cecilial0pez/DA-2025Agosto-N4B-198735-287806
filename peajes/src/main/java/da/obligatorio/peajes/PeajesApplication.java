package da.obligatorio.peajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import da.obligatorio.peajes.modelo.DatosPrueba;
import da.obligatorio.peajes.modelo.PeajeException;

@SpringBootApplication
public class PeajesApplication {

	public static void main(String[] args) throws PeajeException {
		DatosPrueba.cargar(); // <<-- importante
		SpringApplication.run(PeajesApplication.class, args);
	}

}
