package da.obligatorio.peajes.modelo;

import java.util.Date;
import java.util.List;

public class Frecuente extends Bonificacion {

    public Frecuente() {
        super("Frecuente",0.5);
    }

    // Método específico con el contexto necesario para determinar si aplica
    // bonificación frecuente. Frecuentes: Tienen un 50% de descuento a partir del segundo transito realizado en el día
// por un puesto determinado con el mismo vehículo. En el primer tránsito del día (con
// cada vehículo) no tienen descuento
    @Override
    public double calcularBonificacion(Propietario propietario, Vehiculo vehiculo, Puesto puesto, Date fecha) {
        if (propietario.getTransitos() == null) return 0;
       if(vehiculo.cantidadTransitosPorDiaYPuesto(puesto, fecha) >= 1){
           return 0.50;
       }
        return 0.0;
    }
    //desglosar metodo en diferentes clases
    //si en MEJOR MONTO EN ASIGNAR BONIFICAION ES=0 BONIFICACION ES NULL 

}
