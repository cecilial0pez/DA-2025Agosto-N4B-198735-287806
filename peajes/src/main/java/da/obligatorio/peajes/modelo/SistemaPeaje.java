package da.obligatorio.peajes.modelo;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class SistemaPeaje {
    private ArrayList<Puesto> puestosPeaje = new ArrayList();
    private ArrayList<Categoria> categorias = new ArrayList();
    private ArrayList<Estado> estados = new ArrayList();
    private ArrayList<Vehiculo> vehiculos = new ArrayList();
    private ArrayList<Propietario> propietarios = new ArrayList();
    private ArrayList<Bonificacion> bonificaciones = new ArrayList();

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public ArrayList<Puesto> getPuestosPeaje() {
        return puestosPeaje;
    }

    public ArrayList<Bonificacion>getBonificaciones(){
        return bonificaciones;
    }
    public ArrayList<Propietario> getPropietarios() {
        return propietarios;
    }
    //Métodos para agregar datos. 
    public void agregarEstado(Estado e) {
     estados.add(e);
     }

    public void agregarPropietario(Propietario prop) {
        if(this.propietarios==null){
            this.propietarios=new ArrayList<>();
        }
        propietarios.add(prop);
    }

    public void agregarCategoria(Categoria c) {
        if(this.categorias==null){
            this.categorias=new ArrayList<>();
        }
        categorias.add(c);
    }

    public void agregarPuesto(Puesto p) {
        if(this.puestosPeaje==null){
            this.puestosPeaje=new ArrayList<>();
        }
        puestosPeaje.add(p);
    }

    public void agregarBonificacion(Bonificacion b) {
        if(this.bonificaciones==null){
            this.bonificaciones=new ArrayList<>();
        }
        bonificaciones.add(b);
    }

    public void agregarAsignacion(String nombrePuesto, String nombreBonificacion, String cedula) throws PeajeException {     
        Puesto puestoAbuscar=buscarPuesto(nombrePuesto);    
        Bonificacion bonificacionAbuscar=buscarBonificacion(nombreBonificacion);
        Propietario propietarioAbuscar=buscarPropietario(cedula);
        if(!propietarioAbuscar.hayBonificacionEnPuesto(puestoAbuscar)){
            propietarioAbuscar.asignarBonificacion(puestoAbuscar, bonificacionAbuscar);
        }
        else{
            throw new PeajeException("Ya existe una bonificacion para ese puesto");
        }
    }
        
    public void agregarVehiculo(Vehiculo v) {
        if(this.vehiculos==null){
            this.vehiculos=new ArrayList<>();
        }
        vehiculos.add(v);
       Propietario prop = v.getPropietario();
       prop.agregarVehiculo(v);
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

    //Falta arreglar. El saldo del propietario no pareceria estarse descontando. 
    // A su vez, el metodo de bonificacion en lugar de devolver lo que hay 
    //que pagar que devuelva el monto de descuento y ahi se lo paso a 
    //transito y que transito mismo haga el calculo de cual es el precio final
    //falta notificar el saldo minimo de alerta para el propietario 
     public Transito agregarTransito(String matricula, Date fechaHora, String nombrePuesto) throws PeajeException{
        Vehiculo v = buscarVehiculo(matricula);
        Propietario propietario = v.getPropietario();
        Puesto p = buscarPuesto(nombrePuesto);
        Tarifa t = p.TarifaPorCategoria(v.getCategoria());
        if (SePuedeHacerTransito(v, propietario, p, t)) {
            Transito transito = new Transito(v, fechaHora, p, t);
            double totalPagado = t.getMonto();
                if (propietario.hayBonificacionEnPuesto(p)) {
                    Bonificacion bonifAsignada = propietario.bonificacionporPuesto(p);
                    transito.setNombreBonificacion(bonifAsignada.getNombre());
                    totalPagado = bonifAsignada.calcularBonificacion(propietario, v, p, fechaHora);
                }
            transito.setTotalPagado(totalPagado);
            propietario.hacerRegistrarTransito(transito);
            v.incrementarCantidadTransitos();
            p.agregarTransito(transito);
            return transito;
        } else {
            return null;
        }
    }
     
     public boolean SePuedeHacerTransito(Vehiculo v, Propietario prop, Puesto p, Tarifa t) throws PeajeException
     {
       if(v==null){
            throw new PeajeException("No existe vehiculo con esa matricula");
        }else if(prop==null){
            throw new PeajeException("No existe un propietario con esa cedula");
        }else if(p==null){
            throw new PeajeException("No existe un puesto con ese nombre");
        } else if(t==null){
            throw new PeajeException("No existe tarifa para esa categoria en ese puesto");
        }else if(prop.getEstado().getNombre().equals("Suspendido")||prop.getEstado().getNombre().equals("Deshabilitado" )){
            throw new PeajeException("El propietario no puede realizar transitos, ya que su estado es "+ prop.getEstado().getNombre());
        }else{
            return true;
        }
     }

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
        throw new PeajeException("No existe una bonificacion con ese nombre");
    }

    private Vehiculo buscarVehiculo(String matricula) throws PeajeException{
        for (Vehiculo v:vehiculos){
            if(v.getMatricula().equals(matricula)){
                return v;
            }
        }
        throw new PeajeException ("No existe vehiculo con esa matricula");
    }

    public void eliminarNotificaciones(Propietario propietario){
        if(propietario != null) {
            propietario.getNotificaciones().clear();
        }

    }

    public List<Notificacion> getNotificaciones(Propietario propietario){
        if(propietario != null) {
            return propietario.getNotificaciones();
        }
        return new ArrayList<>();
    }

    public List<Tarifa> getTarifasPuesto(String nombrePuesto) throws PeajeException{
        Puesto puestoAbuscar=buscarPuesto(nombrePuesto);    
        if(puestoAbuscar != null){
            return puestoAbuscar.getTarifas();
        }else{
            throw new PeajeException("El puesto buscado no existe");
        }
    }

}
