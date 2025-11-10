
package da.obligatorio.peajes.modelo;

import java.time.DayOfWeek;
import java.util.Date;
import java.time.ZoneId;

public class Trabajador extends Bonificacion {

    public Trabajador() {
        super("Trabajador", 0.8);
    }

    public double calcularBonificacion() {

        return 0;
    }

    @Override
    public double calcularBonificacion(Propietario propietario, Vehiculo vehiculo, Puesto puesto, Date fecha) {

        if (propietario == null || vehiculo == null || puesto == null || fecha == null) {
            return 0;
        }
        if (nombre.equals("Trabajador") && esDiaDeSemana(fecha)) {
            return this.descuento = 0.8;
        }
        return 0;
    }

    /*
     * auxiliar
     * Date.toString() muestra hora según zona local;
     * Instant imprime siempre en UTC (la hora puede “moverse” según la zona).
     */
    public boolean esDiaDeSemana(Date fecha) {
        DayOfWeek dia = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek();
        return dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY;
    }

}
