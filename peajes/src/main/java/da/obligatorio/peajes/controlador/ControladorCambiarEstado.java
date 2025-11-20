package da.obligatorio.peajes.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import da.obligatorio.peajes.ConexionNavegador;
import da.obligatorio.peajes.Respuesta;
import da.obligatorio.peajes.dto.AsignacionDTO;
import da.obligatorio.peajes.dto.NombreDTO;
import da.obligatorio.peajes.dto.PropietarioDTO;
import da.obligatorio.peajes.dto.PropietarioEstadoDTO;
import da.obligatorio.peajes.dto.TransitoDTO;
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.Habilitado;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Penalizado;
import da.obligatorio.peajes.modelo.Propietario;
import da.obligatorio.peajes.modelo.Puesto;
import da.obligatorio.peajes.modelo.Suspendido;
import da.obligatorio.peajes.modelo.TipoEstado;
import da.obligatorio.peajes.modelo.Transito;
import da.obligatorio.peajes.modelo.Asignacion;
import da.obligatorio.peajes.modelo.Deshabilitado;
import da.obligatorio.peajes.modelo.Estado;
import observador.Observable;
import observador.Observador;


@RestController
@Scope("session")
public class ControladorCambiarEstado implements Observador{
 private final ConexionNavegador conexionNavegador; 
    

    public ControladorCambiarEstado(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

   @PostMapping("/cambiarEstado/vistaConectada")
    public List<Respuesta> inicializarVista() {
        List<Respuesta> paquete = new ArrayList<>();
        paquete.add(estados());
        if (cedulaActual != null) {
            paquete.add(new Respuesta("cedulaPropietarioSeleccionada", cedulaActual));
            try {
                paquete.addAll(crearEstadoPropietario(cedulaActual));
            } catch (PeajeException e) {
                paquete.add(new Respuesta("propietarioEstadoError", e.getMessage()));
            }
        }
        return paquete;
    }

    private Respuesta estados() {
        List<TipoEstado> estados = Fachada.getInstancia().getTiposEstado();
        List<NombreDTO>estadosDto = new ArrayList<NombreDTO>();
        for(TipoEstado te: estados) {
             estadosDto.add(new NombreDTO(te.getNombre()));
        }

         return new Respuesta("estados", estadosDto);       
    }

    private String cedulaActual;

    private List<Respuesta> crearEstadoPropietario(String cedula) throws PeajeException {
        Propietario propietario = Fachada.getInstancia().buscarPropietario(cedula);
        PropietarioEstadoDTO dto = new PropietarioEstadoDTO(propietario);
        List<PropietarioEstadoDTO> lista = new ArrayList<>();
        lista.add(dto);
        return Respuesta.lista(new Respuesta("propietarioEstado", lista));
    }

    @PostMapping("/estadoPropietario")
    public List<Respuesta> estadoPropietario(@RequestParam String cedula) {
        try {
            this.cedulaActual = cedula;
            Propietario propietario = Fachada.getInstancia().buscarPropietario(cedula);
            propietario.agregarObservador(this);
            List<Respuesta> paquete = new ArrayList<>();
            paquete.add(new Respuesta("cedulaPropietarioSeleccionada", cedula));
            paquete.addAll(crearEstadoPropietario(cedula));
            return paquete;
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("propietarioEstadoError", e.getMessage()));
        }
    }
    

    @PostMapping("/cambiarEstadoPropietario")
    public List<Respuesta> cambiarEstadoPropietario(@RequestParam String cedula,
                                                    @RequestParam int posTipoEstado) {
        try {
            Propietario propietario = Fachada.getInstancia().buscarPropietario(cedula);
            TipoEstado tipoEstado = Fachada.getInstancia().getTiposEstado().get(posTipoEstado);

            if (propietario.getEstado().getNombre().equals(tipoEstado.getNombre())) {
                return Respuesta.lista(new Respuesta(
                        "cambioEstadoError",
                        "El propietario ya está en estado " + tipoEstado.getNombre() + "."));
            }

            Estado nuevoEstado;
            switch (tipoEstado.getNombre()) {
                case "Habilitado":
                    nuevoEstado = new Habilitado(propietario);
                    break;
                case "Deshabilitado":
                    nuevoEstado = new Deshabilitado(propietario);
                    break;
                case "Suspendido":
                    nuevoEstado = new Suspendido(propietario);
                    break;
                case "Penalizado":
                    nuevoEstado = new Penalizado(propietario);
                    break;
                default:
                    throw new PeajeException("Estado no válido: " + tipoEstado.getNombre());
            }

            propietario.cambiarEstado(nuevoEstado);

            return Respuesta.lista(new Respuesta(
                "cambioEstadoExito",
                "El estado del propietario ha sido cambiado a " + tipoEstado.getNombre() + "."));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("cambioEstadoError", e.getMessage()));
        }
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (cedulaActual == null) return;
        if (evento instanceof Propietario.Eventos ev && ev == Propietario.Eventos.cambioEstado) {
            try {
                conexionNavegador.enviarJSON(crearEstadoPropietario(cedulaActual));
            } catch (PeajeException e) {
                conexionNavegador.enviarJSON(Respuesta.lista(
                    new Respuesta("propietarioEstadoError", e.getMessage())
                ));
            }
        }
    }

}

//     Estados de propietarios - Información básica: nombre del estado.
// Hasta el momento hay 4 estados de propietarios definidos en el sistema, aunque en el
// futuro podrían definirse más. Los estados actualmente definidos son:
// Habilitado: Es el estado por defecto de los propietarios cuando se dan de alta en el
// sistema. El propietario tiene todas las funcionalidades habilitadas.
// Deshabilitado: El usuario no puede ingresar al sistema ni puede realizar tránsitos.
// Tampoco se le pueden asignar bonificaciones.
// Suspendido: El usuario puede ingresar al sistema, pero no puede realizar tránsitos.
// Penalizado: El usuario puede ingresar al sistema, pero no se le registran notificaciones.
// Puede realizar tránsitos, pero no aplican las bonificaciones que tenga asignadas. 
// 1) El administrador ingresa una cedula de identidad de un propietario e indica que desea
//  buscarlo.
// 2) El sistema muestra el nombre completo del propietario y su estado.
// 3) El sistema muestra la lista de estados de propietario definidos en el sistema con el estado
// actual del propietario seleccionado en la lista.
// 4) El usuario selecciona un nuevo estado de la lista e indica que desea cambiarlo.
// 5) El sistema cambia el estado del propietario y registra una notificación al propietario:
//  [Fecha y hora de la notificación] + “Se ha cambiado tu estado en el sistema. Tu estado actual
// es “ + nombre del estado actual. (Esta notificación siempre se registra, sin importar si el estado
// actual o el anterior permiten registrar notificaciones)
// Cursos alternativos:
// 2) No se encuentra un propietario con la cedula especificada. Mensaje “no existe el propietario”
// 5) El estado seleccionado es igual al actual. Mensaje “El propietario ya esta en estado “ +
// nombre del estado actual.








