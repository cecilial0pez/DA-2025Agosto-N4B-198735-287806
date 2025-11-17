package da.obligatorio.peajes.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import da.obligatorio.peajes.ConexionNavegador;
import da.obligatorio.peajes.Respuesta;
import da.obligatorio.peajes.dto.NombreDTO;
import da.obligatorio.peajes.dto.NotificacionDTO;
import da.obligatorio.peajes.dto.PuestoDTO;
import da.obligatorio.peajes.dto.TarifaDTO;
import da.obligatorio.peajes.dto.TransitoDTO;
import da.obligatorio.peajes.dto.VehiculoDTO;
import da.obligatorio.peajes.modelo.Administrador;
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.Notificacion;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Propietario;
import da.obligatorio.peajes.modelo.Puesto;
import da.obligatorio.peajes.modelo.Tarifa;
import da.obligatorio.peajes.modelo.Vehiculo;
import da.obligatorio.peajes.modelo.Transito;
import jakarta.servlet.http.HttpSession;
import observador.Observable;
import observador.Observador;

@RestController
@Scope("session")
public class ControladorEmularTransito implements Observador{
    private final ConexionNavegador conexionNavegador; 
     private Transito transito;
    
    public ControladorEmularTransito(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }
    //actualiza las vistas a tiempo real ESTUDIAR DEFENSA 
    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE(); 
       
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

   
    private List<Respuesta> puestos() {
            List<NombreDTO> puestosDto = new ArrayList<NombreDTO>();
            List<Puesto> puestos = Fachada.getInstancia().getPuestosPeaje();
            for(Puesto p: puestos) {
                puestosDto.add(new NombreDTO(p.getNombre()));
            }

            return Respuesta.lista(new Respuesta("puestos", puestosDto)); 
           
    }

   

     @PostMapping("/tarifasPuesto")
    public List<Respuesta> tarifasPuesto(@RequestParam String nombre) throws PeajeException {
        try{
            List<TarifaDTO> listaDto = new ArrayList<>();
            List<Tarifa> tarifas = Fachada.getInstancia().getTarifasPuesto(nombre);
            if (tarifas != null) {
                for (Tarifa t : tarifas) {
                    listaDto.add(new TarifaDTO(t));
                }
            }
            return Respuesta.lista(new Respuesta("tarifas", listaDto)); 
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("tarifasError", e.getMessage()));
        }
    }

    //lo que pasa es que : transito solo es 1 y el metodo agregar Transito es void y no devuelve el transito hay que arreglar 
    @PostMapping("/transitoEmulado")
    public List<Respuesta> transitoEmulado(@RequestParam int posPuesto, @RequestParam String matricula, @RequestParam Date fecha) throws PeajeException {
        if(posPuesto < 0){
            throw new PeajeException("Seleccione un puesto");
        }
        Puesto puesto = Fachada.getInstancia().getPuestosPeaje().get(posPuesto);
        tarifasPuesto(puesto.getNombre()); //aca llama al metodo qyue muestra tarifas 
       Transito transito= Fachada.getInstancia().agregarTransito(matricula, fecha, puesto.getNombre()); //revisar
        return Respuesta.lista(transito(transito));
    }

    private Respuesta transito(Transito t){
        return new Respuesta("transito",new TransitoDTO(t));
    }
       
    //  @PostMapping("/crearContacto")
    // public List<Respuesta> crearContacto(@RequestParam String nombre, @RequestParam String telefono,@RequestParam int posTipoContacto,@RequestParam int posTipoTelefono) throws AgendaException {
        
    //   
    //    TipoContacto tc = tiposContacto.get(posTipoContacto);
    //    
    //    agenda.crearContacto(nombre, telefono, tc,tt);
    //    return Respuesta.lista(agenda());
    // }

    // private Respuesta agenda(){
    //     return new Respuesta("agenda",new AgendaDto(agenda));
    // }
    // private Respuesta tiposContacto(){

    //     tiposContacto = new ArrayList<TipoContacto>(Fachada.getInstancia().getTiposContacto());

    //     List<NombreDto> tiposDto = new ArrayList<NombreDto>();
        
    //     for(TipoContacto tc:tiposContacto){
    //         tiposDto.add(new NombreDto(tc.getNombre()));
    //     }
    //     return new Respuesta("tiposContacto", tiposDto);
    // }

           
    @Override
    public void actualizar(Object evento, Observable origen) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

}

// //     ctrl k u para descomentar 
// //Curso normal:
// // 1) El sistema muestra la lista de puestos definidos. Información: Nombre del puesto.
// // 2) El administrador selecciona un puesto.
// // 3) El sistema muestra la tabla de tarifas del puesto seleccionado. Información: categoría y monto.
// // 4) El administrador ingresa un número de matrícula, la fecha del tránsito e indica que desea
// // emular un tránsito.
// // 5) El sistema registra un tránsito para el vehículo correspondiente a la matricula ingresada
// //  en la fecha y hora indicada y actualiza el saldo del propietario.
// //  El sistema registra una notificación al propietario:
// //  [Fecha y hora de la notificación] + “Pasaste por el puesto “ + número de puesto + “con
// //  el vehículo” + número de matrícula.
// //  Si el saldo del usuario es menor al valor de la alerta de saldo mínimo el sistema registra
// //  una notificación al propietario: [Fecha y hora de la notificación] + “Tu saldo actual es
// //  de $ “ + saldo actual + “ Te recomendamos hacer una recarga”;
// // 6) El sistema muestra el nombre del propietario del vehículo, su estado, la categoría del vehículo,
// // el nombre de la bonificación si corresponde, el costo del tránsito y el saldo del propietario
// //  luego del tránsito.
// // Cursos alternativos:
// // 5) No existe un vehículo registrado con la matricula ingresada. Mensaje: “No existe el
// //  vehículo”.
// //  El propietario no tiene saldo suficiente para abonar el tránsito. Mensaje: “Saldo
// //  insuficiente:” + el saldo actual del propietario.
// //  El propietario está en estado deshabilitado. Mensaje: “El propietario del vehículo está
// //  deshabilitado, no puede realizar tránsitos” y no se registra el tránsito.
// //  El propietario está en estado suspendido. Mensaje: “El propietario del vehículo está
// //  suspendido, no puede realizar tránsitos” y no se registra el transito
// //  El propietario está en estado penalizado. El transito se registra, pero no se aplican
// //  bonificaciones (si hubiera) y no se envía la notificación al propietario.


// }
