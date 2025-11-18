package da.obligatorio.peajes.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import da.obligatorio.peajes.Respuesta;
import da.obligatorio.peajes.dto.AsignacionDTO;
import da.obligatorio.peajes.dto.NombreDTO;
import da.obligatorio.peajes.dto.PropietarioDTO;
import da.obligatorio.peajes.dto.TransitoDTO;
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Propietario;
import da.obligatorio.peajes.modelo.Puesto;
import da.obligatorio.peajes.modelo.Transito;
import da.obligatorio.peajes.modelo.Asignacion;
import da.obligatorio.peajes.modelo.Estado;

@RestController
@Scope("session")
public class ControladorCambiarEstado {

   @PostMapping("/cambiarEstado/vistaConectada")
    public List<Respuesta> inicializarVista() {
        return Respuesta.lista(estados());
    }

    private Respuesta estados() {
        List<Estado> estados = Fachada.getInstancia().getEstados();
        List<NombreDTO>estadosDto = new ArrayList<NombreDTO>();
        for(Estado e: estados) {
             estadosDto.add(new NombreDTO(e.getNombre()));
        }

         return new Respuesta("estados", estadosDto);       
    }

    @PostMapping("/estadoPropietario")
    public Respuesta estadoPropietario(@RequestParam String cedula) {
        try {
            Propietario propietario=Fachada.getInstancia().getPropietarioPorCedula(cedula);

            List<PropietarioDTO> propietarioDto = new ArrayList<>();
            PropietarioDTO dto = new PropietarioDTO(propietario);
            propietarioDto.add(dto);
            return new Respuesta("propietarioDescripcion", dto.descripcionCorta());
        } catch (PeajeException e) {
            return new Respuesta("propietarioerror", e.getMessage());
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



}
