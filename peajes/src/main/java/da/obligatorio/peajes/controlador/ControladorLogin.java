package da.obligatorio.peajes.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import da.obligatorio.peajes.Respuesta;
import da.obligatorio.peajes.modelo.Administrador;
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Propietario;
import jakarta.servlet.http.HttpSession;

public class ControladorLogin {

  @PostMapping("/loginProp")
    public List<Respuesta> loginPropietario(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String password) throws PeajeException {
        //login al modelo
        Propietario prop = Fachada.getInstancia().loginPropietario(cedula, password);
                
        //guardo el prop en la sesionHttp
        sesionHttp.setAttribute("usuarioProp", prop);
        return Respuesta.lista(new Respuesta("loginExitoso", "monitor-actividad.html"));
    }

    @PostMapping("/loginAdm")
    public List<Respuesta> loginAdministrador(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String password) throws PeajeException {
        //login al modelo
        Administrador adm = Fachada.getInstancia().loginAdministrador(cedula, password);
                
        //guardo el adm en la sesionHttp
        sesionHttp.setAttribute("usuarioAdm", adm);
        return Respuesta.lista(new Respuesta("loginExitoso", "monitor-actividad.html"));
    }
    //verificar si hay que hacer dos logout o uno solo
}
