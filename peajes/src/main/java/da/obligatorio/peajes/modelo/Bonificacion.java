package da.obligatorio.peajes.modelo;

import java.util.Date;

//abstracta para que calcule descuento por polimorfismo
public abstract class Bonificacion {
    public String nombre;
    public double descuento; // porcentaje de descuento

    public Bonificacion(String nombre, double descuento) {
        this.nombre = nombre;
        this.descuento = descuento;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public abstract double calcularBonificacion();

    public abstract double calcularBonificacion(Propietario propietario, Vehiculo vehiculo, Puesto puesto,
            Date fecha);

    /*
     * Metodos agregados
     * public double calcularBonificacion() {
     * if (nombre.equals("Exonerado")) {
     * return descuento = 1.0;
     * } else if (nombre.equals("Frecuente")) {
     * return descuento = 0.5;
     * } else if (nombre.equals("Trabajador")) {
     * return descuento = 0.8;
     * } else {
     * return 0;
     * }
     * }
     */

}
