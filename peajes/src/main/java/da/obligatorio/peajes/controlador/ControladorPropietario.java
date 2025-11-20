package da.obligatorio.peajes.controlador;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import da.obligatorio.peajes.ConexionNavegador;
import da.obligatorio.peajes.Respuesta;
import da.obligatorio.peajes.dto.NotificacionDTO;
import da.obligatorio.peajes.dto.PropietarioDTO;
import da.obligatorio.peajes.dto.TransitoPanelPropietarioDTO;
import da.obligatorio.peajes.dto.VehiculoDTO;
import da.obligatorio.peajes.dto.AsignacionDTO;
import da.obligatorio.peajes.modelo.Asignacion;
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Propietario;
import da.obligatorio.peajes.modelo.Transito;
import da.obligatorio.peajes.modelo.Vehiculo;
import da.obligatorio.peajes.modelo.Notificacion;
import jakarta.servlet.http.HttpSession;
import observador.Observable;
import observador.Observador;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Scope("session")
public class ControladorPropietario implements Observador {

    private final ConexionNavegador conexionNavegador; 
    private Propietario propietarioActual;
    private String cedulaActual;

      @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE(); 
       
    }


    public ControladorPropietario(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }
    
    private Propietario propietarioEnSesion(HttpSession sesionHttp) throws PeajeException {
        if (sesionHttp == null) {
            throw new PeajeException("Sesión nula");
        }
        Object obj = sesionHttp.getAttribute("usuarioProp");
        if (obj == null || !(obj instanceof Propietario)) {
            throw new PeajeException("Sesión expirada o no iniciada");
        }
        Propietario prop = (Propietario) obj;
        String ced = prop.getCedula();
        if (ced == null || ced.trim().isEmpty()) {
            throw new PeajeException("Propietario en sesión inválido (cédula vacía)");
        }
        this.propietarioActual = prop;
        this.cedulaActual = prop.getCedula();
        prop.agregarObservador(this);
        return prop;
    }

    private Respuesta construirInfo(Propietario prop) {
        return new Respuesta("infoProp", new PropietarioDTO(prop));
    }

    private Respuesta construirVehiculos(Propietario prop) {
        List<VehiculoDTO> listaDto = new ArrayList<>();
        List<Vehiculo> vehiculos = prop.getVehiculos();
        if (vehiculos != null) {
            for (Vehiculo v : vehiculos) {
                listaDto.add(new VehiculoDTO(v));
            }
        }
        return new Respuesta("vehiculosProp", listaDto);
    }

    private Respuesta construirTransitos(Propietario prop) {
        List<TransitoPanelPropietarioDTO> listaDto = new ArrayList<>();
        List<Transito> transitos = prop.getTransitos();
        if (transitos != null) {
            transitos.sort(Comparator.comparing(Transito::getFechaHora).reversed());
            for (Transito t : transitos) {
                listaDto.add(new TransitoPanelPropietarioDTO(t));
            }
        }
        return new Respuesta("transitosProp", listaDto);
    }

    private Respuesta construirAsignaciones(Propietario prop) {
        List<AsignacionDTO> listaDto = new ArrayList<>();
        List<Asignacion> asignaciones = prop.getAsignaciones();
        if (asignaciones != null) {
            for (Asignacion a : asignaciones) {
                listaDto.add(new AsignacionDTO(a));
            }
        }
        return new Respuesta("bonificacionesProp", listaDto);
    }

    private Respuesta construirNotificaciones(Propietario prop) {
        List<Notificacion> notisOrigen = prop.getNotificaciones();
        List<Notificacion> notis = notisOrigen == null ? new ArrayList<>() : new ArrayList<>(notisOrigen);
        notis.sort(Comparator.comparing(Notificacion::getFechaHoraEnvio).reversed());

        List<NotificacionDTO> listaDto = new ArrayList<>();
        for (Notificacion n : notis) {
            listaDto.add(new NotificacionDTO(n));
        }
        return new Respuesta("notisProp", listaDto);
    }

    private List<Respuesta> construirPaqueteCompleto(Propietario prop) {
        List<Respuesta> paquete = new ArrayList<>();
        paquete.add(construirInfo(prop));
        paquete.add(construirAsignaciones(prop));
        paquete.add(construirVehiculos(prop));
        paquete.add(construirTransitos(prop));
        paquete.add(construirNotificaciones(prop));
        return paquete;
    }

    @PostMapping("/infoProp")
    public List<Respuesta> infoProp(HttpSession sesionHttp) {
        try {
            Propietario prop = propietarioEnSesion(sesionHttp);
            return Respuesta.lista(construirInfo(prop));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("infoPropError", e.getMessage()));
        }
    }

    @PostMapping("/vehiculosProp")
    public List<Respuesta> vehiculosProp(HttpSession sesionHttp) {
        try {
            Propietario prop = propietarioEnSesion(sesionHttp);
            return Respuesta.lista(construirVehiculos(prop));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("vehiculosPropError", e.getMessage()));
        }
    }

    @PostMapping("/transitosProp")
    public List<Respuesta> transitosProp(HttpSession sesionHttp) {
        try {
            Propietario prop = propietarioEnSesion(sesionHttp);
            return Respuesta.lista(construirTransitos(prop));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("transitosPropError", e.getMessage()));
        }
    }

    @PostMapping("/bonificacionesProp")
    public List<Respuesta> bonificacionesProp(HttpSession sesionHttp) {
        try {
            Propietario prop = propietarioEnSesion(sesionHttp);
            return Respuesta.lista(construirAsignaciones(prop));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("bonificacionesPropError", e.getMessage()));
        }
    }

    @PostMapping("/notisProp")
    public List<Respuesta> notisProp(HttpSession sesionHttp) {
        try {
            Propietario prop = propietarioEnSesion(sesionHttp);
            return Respuesta.lista(construirNotificaciones(prop));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("notisPropError", e.getMessage()));
        }
    }

    @PostMapping("/borrarNotisProp")
    public List<Respuesta> borrarNotisProp(HttpSession sesionHttp) {
        try{
            Propietario prop=propietarioEnSesion(sesionHttp);
            List<Notificacion> notis=prop.getNotificaciones();
            if(notis==null || notis.isEmpty()){
                return Respuesta.lista(new Respuesta("borrarNotisPropError", "No hay notificaciones para borrar"));
            }
            Fachada.getInstancia().borrarNotificacionesPropietario(prop);
            return Respuesta.lista(new Respuesta("borrarNotisProp", "Notificaciones borradas correctamente"));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("borrarNotisPropError", e.getMessage()));
        }
    }
    
    @PostMapping("/panelPropietario/vistaConectada")
    public List<Respuesta> vistaConectada(HttpSession sesionHttp) {
        try {
            Propietario prop = propietarioEnSesion(sesionHttp);
            return construirPaqueteCompleto(prop);
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("panelPropError", e.getMessage()));
        }
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (cedulaActual == null || propietarioActual == null) return;
        if (!(evento instanceof Propietario.Eventos)) return;

        conexionNavegador.enviarJSON(construirPaqueteCompleto(propietarioActual));
    }

    @PostMapping("propietario/vistaCerrada")
    public void vistaCerrada(HttpSession sesion) {
        try {
            propietarioEnSesion(sesion);
            if (cedulaActual != null) {
                Propietario propietario = Fachada.getInstancia().buscarPropietario(cedulaActual);
                propietario.quitarObservador(this);
                cedulaActual = null;
            }
        } catch (PeajeException e) {
           
        }
    }
}
