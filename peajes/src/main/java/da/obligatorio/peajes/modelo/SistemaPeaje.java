package da.obligatorio.peajes.modelo;

import java.util.ArrayList;

public class SistemaPeaje {
    private ArrayList<Puesto> puestosPeaje = new ArrayList();
    private ArrayList<Categoria> categorias = new ArrayList();


public void agregarCategoria(Categoria c){
    categorias.add(c);
}

public void agregarPuesto(Puesto p){
    puestosPeaje.add(p);
}

public ArrayList<Categoria> getCategorias() {
    return categorias;      
}

public ArrayList<Puesto> getPuestosPeaje() {
    return puestosPeaje;
}



}
