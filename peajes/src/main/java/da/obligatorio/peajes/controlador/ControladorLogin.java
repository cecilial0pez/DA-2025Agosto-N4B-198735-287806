package da.obligatorio.peajes.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import da.obligatorio.peajes.Respuesta;
import da.obligatorio.peajes.modelo.Administrador;
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Propietario;
import jakarta.servlet.http.HttpSession;

@RestController
public class ControladorLogin {

    @PostMapping("/loginAdm")
    public List<Respuesta> loginAdministrador(@RequestParam String cedula,
                                              @RequestParam String contrasenia,
                                              HttpSession sesionHttp) {
        try {
            if (sesionHttp.getAttribute("usuarioAdm") != null) {
                throw new PeajeException("Ya hay un administrador activo.");
            }
            Administrador adm = Fachada.getInstancia()
                    .loginAdministrador(cedula, contrasenia);
            sesionHttp.setAttribute("usuarioAdm", adm);
            return Respuesta.lista(new Respuesta("loginAdmExito", "/monitor-actividad.html"));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("loginAdmError", e.getMessage()));
        }
    }

    @PostMapping("/loginProp")
    public List<Respuesta> loginPropietario(@RequestParam String cedula,
                                            @RequestParam String password,
                                            HttpSession sesionHttp) {
        try {
            Propietario prop = Fachada.getInstancia().loginPropietario(cedula, password);
            sesionHttp.setAttribute("usuarioProp", prop);
            return Respuesta.lista(new Respuesta("loginExitoso", "/panel-propietario.html"));
        } catch (PeajeException ex) {
            return Respuesta.lista(new Respuesta("loginError", ex.getMessage()));
        }
    }

    @PostMapping("/logoutAdmin")
    public List<Respuesta> logoutAdmin(HttpSession sesionHttp) {
        if (sesionHttp != null) {
            sesionHttp.removeAttribute("usuarioAdm");
        }
        return Respuesta.lista(new Respuesta("logout", "/login-admin.html"));
    }
}
