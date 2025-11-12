package da.obligatorio.peajes.modelo;

import java.util.Date;

//abstracta para que calcule descuento por polimorfismo
public abstract class Bonificacion {
    private String nombre;
   

    public Bonificacion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public abstract double calcularBonificacion(Propietario propietario, Vehiculo vehiculo, Puesto puesto,
            Date fecha);

}
    

    

   
