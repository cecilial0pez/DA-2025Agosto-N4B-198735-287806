package da.obligatorio.peajes.modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SistemaPeaje {
    private ArrayList<Puesto> puestosPeaje = new ArrayList();
    private ArrayList<Categoria> categorias = new ArrayList();
    private ArrayList<Tarifa> tarifas = new ArrayList();
    private ArrayList<Transito> transitos = new ArrayList();
    private ArrayList<Estado> estados = new ArrayList();
    private ArrayList<Vehiculo> vehiculos = new ArrayList();
    private ArrayList<Notificacion> notificaciones = new ArrayList();

    //si este sistema tiene acceso a todo o para agregar un transito 

public void agregarCategoria(Categoria c){
    categorias.add(c);
}

public void agregarPuesto(Puesto p){
    puestosPeaje.add(p);
}

public void agregarTarifa (Tarifa t){
    tarifas.add(t);
}

public void agregarTransito (Transito t){
    transitos.add(t);
}

public void agregarEstado (Estado e){
    estados.add(e);
}

public void agregarVehiculo (Vehiculo v){
    vehiculos.add(v);
}

public void agregarNotificacion (Notificacion n){
    notificaciones.add(n);
}


public ArrayList<Categoria> getCategorias() {
    return categorias;      
}

public ArrayList<Tarifa> getTarifas() {
    return tarifas;
}

public ArrayList<Puesto> getPuestosPeaje() {
    return puestosPeaje;
}

public ArrayList<Notificacion> getNotificaciones() {
   return notificaciones;
}



}
