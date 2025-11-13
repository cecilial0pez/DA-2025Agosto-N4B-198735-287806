package da.obligatorio.peajes.modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SistemaPeaje {
    private ArrayList<Puesto> puestosPeaje = new ArrayList();
    private ArrayList<Categoria> categorias = new ArrayList();
    // private ArrayList<Tarifa> tarifas = new ArrayList(); ////lo precisa tener
    // solamente el propietario -prof
    private ArrayList<Transito> transitos = new ArrayList();// lo precisa tener solamente el propietario..?
    private ArrayList<Estado> estados = new ArrayList();
    private ArrayList<Vehiculo> vehiculos = new ArrayList();
    // private ArrayList<Notificacion> notificaciones = new ArrayList();//lo precisa
    // tener solamente el propietario -prof

    // si este sistema tiene acceso a todo o para agregar un transito

    public void agregarCategoria(Categoria c) {
        categorias.add(c);
    }

    public void agregarPuesto(Puesto p) {
        puestosPeaje.add(p);
    }

    public void agregarTransito(Transito t) {
        transitos.add(t);
    }

    public void agregarEstado(Estado e) {
        estados.add(e);
    }

    public void agregarVehiculo(Vehiculo v) {
        vehiculos.add(v);
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public ArrayList<Puesto> getPuestosPeaje() {
        return puestosPeaje;
    }

}
