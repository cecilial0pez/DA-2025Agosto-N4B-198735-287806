package da.obligatorio.peajes.modelo;

import java.util.ArrayList;

public class SistemaPeaje {
    private ArrayList<Puesto> puestosPeaje = new ArrayList();
    private ArrayList<Categoria> categorias = new ArrayList();
    private ArrayList<Tarifa> tarifas = new ArrayList();
    private ArrayList<Transito> transitos = new ArrayList();


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

public ArrayList<Categoria> getCategorias() {
    return categorias;      
}

public ArrayList<Tarifa> getTarifas() {
    return tarifas;
}

public ArrayList<Puesto> getPuestosPeaje() {
    return puestosPeaje;
}



}
