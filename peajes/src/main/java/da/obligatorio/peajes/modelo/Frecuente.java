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
            if(propietario.getTransitosPorDiaYPuesto(puesto, fecha)>=1){
                double precioFinal=monto -(monto* this.getPorcentajeDescuento());
            }else{
                double precioFinal=monto;
            }  
            return precioFinal;
        } catch (Exception e) {
            throw new PeajeException("Error al calcular bonificacion: Categoria no encontrada");
        }
    }

}
 //     if (propietario.getTransitos() == null) return 0;
    //    if(vehiculo.cantidadTransitosPorDiaYPuesto(puesto, fecha) >= 1){
    //        return 0.50;
    //    }
    //     return 0.0;