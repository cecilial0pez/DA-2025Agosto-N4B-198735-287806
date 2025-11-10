package da.obligatorio.peajes.controlador;


import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import da.obligatorio.peajes.ConexionNavegador;
import da.obligatorio.peajes.Respuesta;
import da.obligatorio.peajes.modelo.Administrador;
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Propietario;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/menu")
public class ControladorMenu {

    private final ObjectProvider<da.obligatorio.peajes.ConexionNavegador> conexionProvider;

    @Autowired
    public ControladorMenu(ObjectProvider<da.obligatorio.peajes.ConexionNavegador> conexionProvider) {
        this.conexionProvider = conexionProvider;
    }

    // dentro de métodos que manejan requests:
    private void usarConexion() {
        var conexion = conexionProvider.getIfAvailable();
        if (conexion != null) {
            // usar conexion (asociada a la sesión actual)
        }
    }

   @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        var conexion = conexionProvider.getIfAvailable();
        if (conexion != null) {
            conexion.conectarSSE();
            return conexion.getConexionSSE(); 
        }
        return null;
       
    }

   private Administrador administradorEnSesion(HttpSession sesionHttp) throws PeajeException {
        if (sesionHttp == null) throw new PeajeException("Sesión nula");
        Object obj = sesionHttp.getAttribute("usuarioAdm");
        if (obj instanceof Administrador adm) {
            String ced = adm.getCedula();
            if (ced != null && !ced.trim().isEmpty()) {
                return adm; 
            }
            throw new PeajeException("Administrador en sesión inválido (cedula vacía)");
        }
        throw new PeajeException("Sesión expirada o no iniciada");
    }

    @PostMapping("/cargarMenu")
    public List<Respuesta> cargarMenu(HttpSession sesionHttp) {
        try {
            Administrador admin = administradorEnSesion(sesionHttp);

            return Respuesta.lista(new Respuesta("nombre", admin.getNombre()));

        } catch (PeajeException e) {
             return Respuesta.lista(new Respuesta("menuError", e.getMessage()));
        }
    }


}







