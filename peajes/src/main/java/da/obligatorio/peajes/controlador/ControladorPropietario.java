package da.obligatorio.peajes.controlador;

import java.util.ArrayList;
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
import da.obligatorio.peajes.dto.VehiculoDTO;
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Propietario;
import da.obligatorio.peajes.modelo.Vehiculo;
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

     private Respuesta notificaciones(){
        return  new Respuesta("notificaciones", NotificacionDTO.listaNotificacionesDto(Fachada.getInstancia().getNotificaciones()));
    }

    @PostMapping("/infoProp")
    public List<Respuesta> infoProp(HttpSession sesionHttp) {
        try {
            Propietario prop = propietarioEnSesion(sesionHttp);
            PropietarioDTO dto = new PropietarioDTO(prop);
            return Respuesta.lista(new Respuesta("infoProp", dto));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("infoPropError", e.getMessage()));
        }
    }

     @PostMapping("/vehiculosProp")
    public List<Respuesta> vehiculosProp(HttpSession sesionHttp) {
        try {
            Propietario prop = propietarioEnSesion(sesionHttp);

            List<VehiculoDTO> listaDto = new ArrayList<>();
            List<Vehiculo> vehiculos = prop.getVehiculos();
            if (vehiculos != null) {
                for (Vehiculo v : vehiculos) {
                    listaDto.add(new VehiculoDTO(v));
                }
            }

            return Respuesta.lista(new Respuesta("vehiculosProp", listaDto));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("vehiculosPropError", e.getMessage()));
        }
    }

    @PostMapping("/transitosProp")
    public List<Respuesta> transitosProp(HttpSession sesionHttp) {
        try {
            Propietario prop = propietarioEnSesion(sesionHttp);

            List<TransitoDTO> listaDto = new ArrayList<>();
            List<Transito> transitos = prop.getTransitos();
            if (transitos != null) {
                for (Transito t : transitos) {
                    listaDto.add(new TransitoDTO(t));
                }
            }

            return Respuesta.lista(new Respuesta("transitosProp", listaDto));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("transitosPropError", e.getMessage()));
        }
    }

    @PostMapping("/notisProp")
    public List<Respuesta> notisProp(HttpSession sesionHttp) {
        try{
            Propietario prop=propietarioEnSesion(sesionHttp)
            List<NotificacionDTO> listaDto=new ArrayList<>();
            List<Notificacion> notis=prop.getNotificaciones();
            if(notis!=null){
                for(Notificacion n:notis){
                    listaDto.add(new NotificacionDTO(n));
                }
            }
            return Respuesta.lista(new Respuesta("notisProp", listaDto));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("notisPropError", e.getMessage()));
        }
    }

    @PostMapping("/borrarNotisProp"){
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
    
   @Override
   public void actualizar(Object evento, Observable origen) {
    // TODO Auto-generated method stub
    if(evento.equals(Fachada.Eventos.cambioListaNotificaciones)){
            conexionNavegador.enviarJSON(Respuesta.lista(notificaciones()));
        }
   }

//    Curso normal:
// 1) El sistema muestra:
// • Nombre completo del propietario
// • Estado
// • Saldo actual
// • Tabla con las bonificaciones asignadas- Información: Nombre de la bonificación, puesto,
// fecha de asignada.
// • Tabla de vehículos registrados – Información: Número de matrícula, modelo, color, cantidad
// de tránsitos realizados y monto total gastado en sus tránsitos.
// • Tabla de tránsitos realizados ordenados por fecha/hora descendente – Información:
// Nombre del puesto, número de matrícula, nombre de la tarifa, monto de la tarifa, nombre de la
// bonificación, monto de la bonificación, monto pagado, fecha y hora.
// • Tabla de notificaciones del sistema ordenados por fecha/hora descendente. Información:
// Fecha y hora, mensaje
// 2) Opcionalmente el propietario indica que desea borrar las notificaciones recibidas.
//  El sistema borra todas las notificaciones del propietario.
// Cursos alternativos:
// 2) En caso de que el propietario no tenga notificaciones se muestra mensaje “No hay
// notificaciones para borrar”


    //  @Override
    //  public void actualizar(Object evento, Observable origen) {
    //     if(evento.equals(Agenda.Eventos.cambioListaContactos) || evento.equals(Agenda.Eventos.cambioEstado)){
    //         conexionNavegador.enviarJSON(Respuesta.lista(agenda()));
    //     }


    


}
