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
import da.obligatorio.peajes.modelo.Asignacion;
import da.obligatorio.peajes.modelo.Bonificacion;
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Propietario;
import da.obligatorio.peajes.modelo.Puesto;
import da.obligatorio.peajes.modelo.Tarifa;
import da.obligatorio.peajes.modelo.Transito;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Scope("session")
public class ControladorBonificaciones {
 

//OBSERVA AL PROPIETARIO 
    @PostMapping("/bonificaciones/vistaConectada")
    public List<Respuesta> inicializarVista() {
        return Respuesta.lista(puestos(),bonificaciones());
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
    public List<Respuesta> asignacionesPropietario(@RequestParam String cedula) {
        try {
            List<AsignacionDTO> asignacionesDto = new ArrayList<>();
            List<Asignacion> asignaciones = Fachada.getInstancia().getAsignacionesPropietario(cedula);
            if (asignaciones != null) {
                for (Asignacion a : asignaciones) {
                    asignacionesDto.add(new AsignacionDTO(a));
                }
            }
            return Respuesta.lista(new Respuesta("asignacionesPropietario", asignacionesDto));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("asignacionesPropietarioerror", e.getMessage()));
        }
    }

     @PostMapping("/asignarBonificacion")
    public void asignarBonificacion(@RequestParam int posPuesto, @RequestParam int posBonificacion, @RequestParam String cedula) throws PeajeException {
        if(posPuesto < 0){
            throw new PeajeException("Debe especificar un puesto");
        }
         if(posBonificacion < 0){
            throw new PeajeException("Debe especificar una bonificación");
        }
        Puesto puesto = Fachada.getInstancia().getPuestosPeaje().get(posPuesto);
        Bonificacion bonificacion = Fachada.getInstancia().getBonificaciones().get(posBonificacion);
        Fachada.getInstancia().agregarAsignacion(puesto.getNombre(), bonificacion.getNombre(),cedula);
    }


  
}

  
// Curso normal:
// 1) El sistema muestra la lista de bonificaciones definidas. Información: Nombre de la
// bonificación. hecho
// 2) El sistema muestra la lista de puestos definidos. Información: Nombre del puesto. hecho
// 3) El administrador ingresa una cedula de identidad de un propietario e indica que desea
//  buscarlo.
// 4) El sistema muestra el nombre completo del propietario, su estado y las bonificaciones que
// tiene asignadas. Información: Nombre de la bonificación - nombre del puesto al que está
// asignada.
// 5) Opcionalmente el administrador selecciona una bonificación de la lista de bonificaciones
// definidas, selecciona un puesto, e indica que desea asignar la bonificación al propietario.
// 6) El sistema asigna la bonificación al propietario para el puesto seleccionado y registra la fecha
// y hora de asignada.
//
// Cursos alternativos:
// 4) No se encuentra un propietario con la cedula especificada. Mensaje “no existe el propietario”
// 6) No hay una bonificación seleccionada. Mensaje “Debe especificar una bonificación”
//  No hay un puesto seleccionado. Mensaje “Debe especificar un puesto”
//  El propietario ya tiene una bonificación para el puesto seleccionado. Mensaje “Ya tiene
//  una bonificación asignada para ese puesto”.
//  El propietario está deshabilitado. Mensaje “El propietario esta deshabilitado. No se pueden
// asignar bonificaciones”.





