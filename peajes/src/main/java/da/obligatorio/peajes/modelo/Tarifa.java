package da.obligatorio.peajes.modelo;

import java.util.List;

public class Tarifa {
    private double monto;
    private Categoria categoria;
   

    public Tarifa(double monto, Categoria categoria) {
        this.monto = monto;
        this.categoria = categoria;
    }
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
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
