package da.obligatorio.peajes.modelo;

import java.util.Date;
import java.util.List;

public class Frecuente extends Bonificacion {

    public Frecuente() {
        super("Frecuente",0.5);
    }

    @Override
    public double calcularBonificacion(Propietario propietario, Vehiculo vehiculo, Puesto puesto, Date fecha) throws PeajeException {
        try {
            if(vehiculo.cantidadTransitosPorDiaYPuesto(puesto, fecha)>=1){
                return this.getPorcentajeDescuento();
            } 
            return 0.0;
        } catch (Exception e) {
            throw new PeajeException("Error al calcular bonificacion" + e.getMessage());
        }
    }

}
