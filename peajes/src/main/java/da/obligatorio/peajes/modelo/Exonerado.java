package da.obligatorio.peajes.modelo;

import java.util.Date;

public class Exonerado extends Bonificacion {

    public Exonerado() {
        super("Exonerado",1.0);
    }

    
    @Override
    public double calcularBonificacion(Propietario propietario, Vehiculo vehiculo, Puesto puesto, Date fecha) {

        if (propietario == null || vehiculo == null || puesto == null || fecha == null) {
            return 0;
        }
        double  descuento= 1.0;
        // Si todos los parámetros son válidos, se aplica la bonificación
        return  descuento;
    }

}