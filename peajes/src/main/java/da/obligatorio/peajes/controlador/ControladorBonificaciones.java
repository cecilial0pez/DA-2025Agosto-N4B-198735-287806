package da.obligatorio.peajes.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import da.obligatorio.peajes.ConexionNavegador;
import da.obligatorio.peajes.Respuesta;
import da.obligatorio.peajes.dto.AsignacionDTO;
import da.obligatorio.peajes.dto.NombreDTO;
import da.obligatorio.peajes.dto.TarifaDTO;
import da.obligatorio.peajes.modelo.Asignacion;
import da.obligatorio.peajes.modelo.Bonificacion;
import da.obligatorio.peajes.modelo.Fachada;
import da.obligatorio.peajes.modelo.PeajeException;
import da.obligatorio.peajes.modelo.Puesto;
import da.obligatorio.peajes.modelo.Tarifa;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Scope("session")
public class ControladorBonificaciones {
 

//OBSERVA AL PROPIETARIO 
    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista() {
        List<Respuesta> respuestas = new ArrayList<>();
        respuestas.addAll(puestos());
        respuestas.addAll(bonificaciones());
        return respuestas;
    }

    private List<Respuesta> puestos() {
        List<NombreDTO> puestosDto = new ArrayList<NombreDTO>();
        List<Puesto> puestos = Fachada.getInstancia().getPuestosPeaje();
        for(Puesto p: puestos) {
             puestosDto.add(new NombreDTO(p.getNombre()));
        }

        return Respuesta.lista(new Respuesta("puestos", puestosDto)); 
           
    }

    private List<Respuesta> bonificaciones() {
        List<NombreDTO> bonificacionesDto = new ArrayList<NombreDTO>();
        List<Bonificacion> bonificaciones = Fachada.getInstancia().getBonificaciones();
        for(Bonificacion b: bonificaciones) {
             bonificacionesDto.add(new NombreDTO(b.getNombre()));
        }

        return Respuesta.lista(new Respuesta("bonificaciones", bonificacionesDto)); 
           
    }

    @PostMapping("/asignacionesPropietario")
    public List<Respuesta> asignacionesPropietario(@RequestBody String cedula) throws PeajeException {
        List<AsignacionDTO> asignacionesDto = new ArrayList<AsignacionDTO>();
        List<Asignacion> asignaciones = Fachada.getInstancia().getAsignacionesPropietario(cedula);
         if (asignaciones != null) {
                for (Asignacion a : asignaciones) {
                    asignacionesDto.add(a);
                }
            }
            return Respuesta.lista(new Respuesta("asignacionesPropietario", asignacionesDto)); 
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("asignacionesPropietarioerror", e.getMessage()));
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




 }
