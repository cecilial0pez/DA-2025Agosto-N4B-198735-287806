package da.obligatorio.peajes.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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


@RestController
@Scope("session")
public class ControladorEmularTransito {
   

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

    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(HttpSession sesion) throws PeajeException {
        administradorEnSesion(sesion);
        return puestos();
    }
   
    private List<Respuesta> puestos() {
            List<NombreDTO> puestosDto = new ArrayList<NombreDTO>();
            List<Puesto> puestos = Fachada.getInstancia().getPuestosPeaje();
            for(Puesto p: puestos) {
                puestosDto.add(new NombreDTO(p.getNombre()));
            }

            return Respuesta.lista(new Respuesta("puestos", puestosDto)); 
           
    }

    @GetMapping("/tarifasPuesto")
    public List<Respuesta> tarifasPuesto(@RequestParam int indPuesto,  HttpSession sesion) {
        try {
            administradorEnSesion(sesion);
            Puesto puesto = Fachada.getInstancia().getPuestosPeaje().get(indPuesto);
            List<TarifaDTO> listaDto = new ArrayList<>();
            List<Tarifa> tarifas = Fachada.getInstancia().getTarifasPuesto(puesto.getNombre());
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

    @PostMapping("/transitoEmulado")
    public List<Respuesta> transitoEmulado(@RequestParam int posPuesto, @RequestParam String matricula, @RequestParam Long fechaHora, HttpSession sesion) {
        try {
            if (posPuesto < 0) {
                throw new PeajeException("Seleccione un puesto");
            }
            Puesto puesto = Fachada.getInstancia().getPuestosPeaje().get(posPuesto);
            Date fecha = new Date(fechaHora);
            Transito transito = Fachada.getInstancia().agregarTransito(matricula, fecha, puesto.getNombre());
            Propietario propietario = transito.getVehiculo().getPropietario();
            TransitoDTO dto = new TransitoDTO(transito, propietario);
            return Respuesta.lista(new Respuesta("transitoEmulado", List.of(dto)));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("transitoEmuladoError", e.getMessage()));
        }
    }

  
                  
  

}

