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
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Propietario;
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
            
            return Respuesta.lista(new Respuesta("infoProp", dto));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("infoPropError", e.getMessage()));
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

  


    // private Respuesta tiposContacto(){

    //     tiposContacto = new ArrayList<TipoContacto>(Fachada.getInstancia().getTiposContacto());

    //     List<NombreDto> tiposDto = new ArrayList<NombreDto>();
        
    //     for(TipoContacto tc:tiposContacto){
    //         tiposDto.add(new NombreDto(tc.getNombre()));
    //     }
    //     return new Respuesta("tiposContacto", tiposDto);
    // }
    //  private Respuesta tiposTelefono(){

    //     tiposTelefono = new ArrayList<TipoTelefono>(Fachada.getInstancia().getTiposTelefono());

    //     List<NombreDto> tiposDto = new ArrayList<NombreDto>();
        
    //     for(TipoTelefono tt:tiposTelefono){
    //         tiposDto.add(new NombreDto(tt.getNombre()));
    //     }
    //     return new Respuesta("tiposTelefono", tiposDto);
    // }
    //  @Override
    //  public void actualizar(Object evento, Observable origen) {
    //     if(evento.equals(Agenda.Eventos.cambioListaContactos) || evento.equals(Agenda.Eventos.cambioEstado)){
    //         conexionNavegador.enviarJSON(Respuesta.lista(agenda()));
    //     }
    //  }



    


}
