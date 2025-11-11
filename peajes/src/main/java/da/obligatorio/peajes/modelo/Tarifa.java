package da.obligatorio.peajes.modelo;

import java.util.List;

public class Tarifa {
    public double Monto;
    public List<Categoria> categorias;
    public List<Puesto> puestos;

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

   
    public List<Puesto> getPuestos() {
        return puestos;
    }

    public void setPuestos(List<Puesto> puestos) {
        this.puestos = puestos;
    }

    public Tarifa(double monto) {
        Monto = monto;
    }

    public double getMonto() {
        return Monto;
    }

    public void setMonto(double monto) {
        Monto = monto;
    }

    //Metodos agregados
    public boolean verificarPuesto(Puesto unPuesto){
       if(unPuesto != null){
        return true;
       } return false;
    }

    public boolean verificarCategoria(Categoria unaCategoria){
        if(unaCategoria != null){
         return true;
        } return false;
     }
}
