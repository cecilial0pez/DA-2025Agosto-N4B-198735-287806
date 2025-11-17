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
            Categoria categoria=vehiculo.getCategoria();
            Tarifa tarifa=puesto.TarifaPorCategoria(categoria);
            Double monto=tarifa.getMonto();
            if(vehiculo.cantidadTransitosPorDiaYPuesto(puesto, fecha)>=1){
                double precioFinal=monto -(monto* this.getPorcentajeDescuento());
                return precioFinal;
            } 
            return monto;
        } catch (Exception e) {
            throw new PeajeException("Error al calcular bonificacion" + e.getMessage());
        }
    }

}
