package da.obligatorio.peajes.modelo;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;

public class SistemaPeaje {
    private ArrayList<Puesto> puestosPeaje = new ArrayList();
    private ArrayList<Categoria> categorias = new ArrayList();
    private ArrayList<Estado> estados = new ArrayList();
    private ArrayList<Vehiculo> vehiculos = new ArrayList();
    private ArrayList<Propietario> propietarios = new ArrayList();
    private ArrayList<Bonificacion> bonificaciones = new ArrayList();
    // private ArrayList<Notificacion> notificaciones = new ArrayList();//lo precisa
    // tener solamente el propietario -prof

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public ArrayList<Puesto> getPuestosPeaje() {
        return puestosPeaje;
    }

    //Métodos para agregar datos. 
    public void agregarEstado(Estado e) {
     estados.add(e);
     }

    public void agregarPropietario(Propietario prop) {
        propietarios.add(prop);
    }

    public void agregarCategoria(Categoria c) {
        categorias.add(c);
    }

    public void agregarPuesto(Puesto p) {
        puestosPeaje.add(p);
    }

  
    public void agregarVehiculo(Vehiculo v) {
        vehiculos.add(v);
    }  


    public void agregarTarifa(String nombrePuesto, String nombreCategoria, double monto) throws PeajeException {
        Puesto puestoAbuscar=buscarPuesto(nombrePuesto);    
        Categoria categoriaAbuscar=buscarCategoria(nombreCategoria);
        if(puestoAbuscar != null && categoriaAbuscar != null){
            Tarifa tarifa = new Tarifa(monto,categoriaAbuscar);
            puestoAbuscar.getTarifas().add(tarifa);
        }else{
            throw new PeajeException("El puesto buscado o la cateogria buscada no existen");
        }
    }

    public void agregarTransito(Transito t, Puesto p) {
        p.getTransitos().add(t);
    }

    public void AgregarAsignacionBonificacion(String nombrePuesto,String nombreBonificacion,String cedula ) throws PeajeException{
        Puesto puestoAbuscar=buscarPuesto(nombrePuesto);    
        Propietario propietarioAbuscar=buscarPropietario(cedula);
        Bonificacion bonificacionAbuscar=buscarBonificacion(nombreBonificacion);
        if(puestoAbuscar != null && propietarioAbuscar != null && bonificacionAbuscar != null){
            if(propietarioAbuscar.puedeBonificarse(puestoAbuscar)){
                propietarioAbuscar.asignarBonificacion(puestoAbuscar, bonificacionAbuscar);
            }
            else{
                throw new PeajeException("Ya existe una bonificacion para ese puesto");
            }
        }
    }

    public void AgregarTransito()

    // public void AgregarNotificacion(Notificacion n){
    //     // notificaciones.add(n);
    // }

    //Metodos de busqueda
     private Puesto buscarPuesto(String nombrePuesto) throws PeajeException {
        for (Puesto p : puestosPeaje) {
            if (p.getNombre().equals(nombrePuesto)) {
                return p;
            }
        }
        throw new PeajeException("No existe un puesto con ese nombre");
    }
    
    private Categoria buscarCategoria(String nombreCategoria) throws PeajeException {
        for (Categoria c : categorias) {
            if (c.getNombre().equals(nombreCategoria)) {
                return c;
            }
        }
        throw new PeajeException("No existe una categoría con ese nombre ");
    }

    private Propietario buscarPropietario(String cedula) throws PeajeException {
        for (Propietario p : propietarios) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        throw new PeajeException("No existe un propietario con esa cédula ");
    }

    private Bonificacion buscarBonificacion(String nombre) throws PeajeException{
        for (Bonificacion b:bonificaciones){
            if(b.getNombre().equals(nombre)){
                return b;
            }
        }
        throw new PeajeException("No existe una bonificacion con ese nombre")
    }

    private Vehiculo buscarVehiculo(String matricula) throws PeajeException{
        for (Vehiculo v:vehiculos){
            if(v.getMatricula().equals(matricula)){
                return v;
            }
        }
        throw new PeajeException ("No existe vehiculo con esa matricula")
    }



}
