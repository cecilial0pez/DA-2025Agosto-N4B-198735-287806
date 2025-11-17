package da.obligatorio.peajes.modelo;

import java.util.Date;

//abstracta para que calcule descuento por polimorfismo
public abstract class Bonificacion {
    private String nombre;
   private double descuento;

    public Bonificacion(String nombre, double descuento) {
        this.nombre = nombre;
        this.descuento = descuento;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPorcentajeDescuento(){
        return descuento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public abstract double calcularBonificacion(Propietario propietario, Vehiculo vehiculo, Puesto puesto,Date fecha)throws PeajeException;
     

}
    

    

   
