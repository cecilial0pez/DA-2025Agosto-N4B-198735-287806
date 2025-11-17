
package da.obligatorio.peajes.modelo;

import java.time.DayOfWeek;
import java.util.Date;
import java.time.ZoneId;

public class Trabajador extends Bonificacion {

    public Trabajador() {
        super("Trabajador",0.8);
    }

    
    @Override
    public double calcularBonificacion(Propietario propietario, Vehiculo vehiculo, Puesto puesto, Date fecha) throws PeajeException   {
        try {
            Categoria categoria = vehiculo.getCategoria();
            Tarifa tarifa = puesto.TarifaPorCategoria(categoria);
            if(esDiaDeSemana(fecha)) { 
            Double monto = tarifa.getMonto();
            double precioFinal = monto - (monto * this.getPorcentajeDescuento());
            return precioFinal;
            }
            return tarifa.getMonto();
        } catch (Exception e) {
            throw new PeajeException("Error al calcular bonificacion: Categoria no encontrada");
        }
        
    }

    /*
     * auxiliar
     * Date.toString() muestra hora según zona local;
     * Instant imprime siempre en UTC (la hora puede “moverse” según la zona).
     *  if (esDiaDeSemana(fecha)) {
            return descuento;
        }
     */
    public boolean esDiaDeSemana(Date fecha) {
        DayOfWeek dia = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek();
        return dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY;
    }

}
