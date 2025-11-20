package da.obligatorio.peajes.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import da.obligatorio.peajes.ConexionNavegador;
import da.obligatorio.peajes.Respuesta;
import da.obligatorio.peajes.dto.AsignacionDTO;
import da.obligatorio.peajes.dto.NombreDTO;
import da.obligatorio.peajes.dto.TarifaDTO;
import da.obligatorio.peajes.dto.TransitoDTO;
import da.obligatorio.peajes.modelo.Administrador;
import da.obligatorio.peajes.modelo.Asignacion;
import da.obligatorio.peajes.modelo.Bonificacion;
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Propietario;
import da.obligatorio.peajes.modelo.Puesto;
import da.obligatorio.peajes.modelo.Tarifa;
import da.obligatorio.peajes.modelo.Transito;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@Scope("session")
public class ControladorBonificaciones {

    private String cedulaActual;
    private List<AsignacionDTO> asignacionesActuales = new ArrayList<>();

      private Administrador administradorEnSesion(HttpSession sesion) throws PeajeException {
        if (sesion == null) {
            throw new PeajeException("Sesión nula");
        }
        Object obj = sesion.getAttribute("usuarioAdm");
        if (!(obj instanceof Administrador)) {
            throw new PeajeException("Sesión expirada o no iniciada");
        }
        return (Administrador) obj;
    }

    @PostMapping("/bonificaciones/vistaConectada")
    public List<Respuesta> inicializarVista(HttpSession sesion) throws PeajeException {
        administradorEnSesion(sesion);
        List<Respuesta> paquete = new ArrayList<>();
        paquete.add(puestos());
        paquete.add(bonificaciones());
        if (cedulaActual != null) {
            paquete.add(new Respuesta("cedulaPropietarioSeleccionada", cedulaActual));
            paquete.add(new Respuesta("asignacionesPropietario", asignacionesActuales));
        }
        return paquete;
    }

    @RequestMapping(value = "/bonificaciones/vistaConectada")
    public List<Respuesta> vistaConectada() {
        List<Respuesta> paquete = new ArrayList<>();
        paquete.add(puestos());
        paquete.add(bonificaciones());
        if (cedulaActual != null) {
            paquete.add(new Respuesta("cedulaPropietarioSeleccionada", cedulaActual));
            paquete.add(new Respuesta("asignacionesPropietario", asignacionesActuales));
        }
        return paquete;
    }

    private Respuesta puestos() {
        
        List<Puesto> puestos = Fachada.getInstancia().getPuestosPeaje();
        List<NombreDTO> puestosDto = new ArrayList<NombreDTO>();
        for(Puesto p: puestos) {
             puestosDto.add(new NombreDTO(p.getNombre()));
        }

         return new Respuesta("puestos", puestosDto); 
           
    }

    private Respuesta bonificaciones() {
        List<Bonificacion> bonificaciones = Fachada.getInstancia().getBonificaciones();
        List<NombreDTO> bonificacionesDto = new ArrayList<NombreDTO>();
        for(Bonificacion b: bonificaciones) {
             bonificacionesDto.add(new NombreDTO(b.getNombre()));
        }

        return new Respuesta("bonificaciones", bonificacionesDto); 
           
    }

    @PostMapping("/asignacionesPropietario")
    public List<Respuesta> asignacionesPropietario(@RequestParam String cedula,
                                                   HttpSession sesion) {
        try {
            administradorEnSesion(sesion);
            this.cedulaActual = cedula;
            this.asignacionesActuales = construirAsignaciones(cedula);
            return Respuesta.lista(
                new Respuesta("asignacionesPropietario", asignacionesActuales)
            );
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("asignacionBonificacionError", e.getMessage()));
        }
    }

    @PostMapping("/asignarBonificacion")
    public List<Respuesta> asignarBonificacion(@RequestParam int posPuesto,
                                               @RequestParam int posBonificacion,
                                               @RequestParam String cedula,
                                               HttpSession sesion) {
        try {
            administradorEnSesion(sesion);
            if (posPuesto < 0) {
                throw new PeajeException("Debe especificar un puesto");
            }
            if (posBonificacion < 0) {
                throw new PeajeException("Debe especificar una bonificación");
            }

            Puesto puesto = Fachada.getInstancia().getPuestosPeaje().get(posPuesto);
            Bonificacion bonificacion = Fachada.getInstancia().getBonificaciones().get(posBonificacion);
            Fachada.getInstancia().agregarAsignacion(puesto.getNombre(), bonificacion.getNombre(), cedula);

            this.cedulaActual = cedula;
            this.asignacionesActuales = construirAsignaciones(cedula);

            return Respuesta.lista(
                new Respuesta("asignacionBonificacionExito", "La bonificación fue asignada correctamente."),
                new Respuesta("asignacionesPropietario", asignacionesActuales)
            );
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("asignacionBonificacionError", e.getMessage()));
        }
    }

    private List<AsignacionDTO> construirAsignaciones(String cedula) throws PeajeException {
        List<AsignacionDTO> asignacionesDto = new ArrayList<>();
        List<Asignacion> asignaciones = Fachada.getInstancia().getAsignacionesPropietario(cedula);
        if (asignaciones != null) {
            for (Asignacion a : asignaciones) {
                asignacionesDto.add(new AsignacionDTO(a));
            }
        }
        return asignacionesDto;
    }


  
}







