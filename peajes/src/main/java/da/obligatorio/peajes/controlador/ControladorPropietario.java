package da.obligatorio.peajes.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import da.obligatorio.peajes.ConexionNavegador;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Propietario;
import jakarta.servlet.http.HttpSession;

@RestController
@Scope("session")
public class ControladorPropietario {
    private final ConexionNavegador conexionNavegador; 

    public ControladorPropietario(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE(); 
       
    }

    //con esto se que propietario esta conectado
   private Propietario propietarioEnSesion(HttpSession sesionHttp) throws PeajeException {
        if (sesionHttp == null) throw new PeajeException("Sesión nula");
        Object obj = sesionHttp.getAttribute("usuarioProp");
        if (obj instanceof Propietario prop) {
            String ced = prop.getCedula();
            if (ced != null && !ced.trim().isEmpty()) {
                return prop;
            }
            throw new PeajeException("Propietario en sesión inválido (cedula vacía)");
        }
        throw new PeajeException("Sesión expirada o no iniciada");
    }

    


}
