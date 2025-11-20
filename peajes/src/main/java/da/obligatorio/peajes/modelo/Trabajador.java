
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
            if(esDiaDeSemana(fecha)) { 
                return this.getPorcentajeDescuento();
            }
            return 0.0;
        } catch (Exception e) {
            throw new PeajeException("Error al calcular bonificacion");
        }
        
    }

 
    public boolean esDiaDeSemana(Date fecha) {
        DayOfWeek dia = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek();
        return dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY;
    }

}
