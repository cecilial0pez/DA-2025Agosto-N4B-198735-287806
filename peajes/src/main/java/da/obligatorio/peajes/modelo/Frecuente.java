package da.obligatorio.peajes.modelo;

import java.util.Date;
import java.util.List;

public class Frecuente extends Bonificacion {

    public Frecuente() {
        super("Frecuente", 0.5);
    }

    // Implementación requerida por la clase abstracta Bonificacion
    @Override
    public double calcularBonificacion() {
        return 0;
    }

    // Método específico con el contexto necesario para determinar si aplica
    // bonificación frecuente.
    @Override
    public double calcularBonificacion(Propietario propietario, Vehiculo vehiculo, Puesto puesto, Date fecha) {
        if (propietario == null || vehiculo == null || puesto == null || fecha == null) {
            return 0;
        }

        List<Transito> transitos = propietario.transitos;
        if (transitos == null || transitos.isEmpty()) {
            return 0;
        }
        String matActual = vehiculo.getMatricula();
        if (matActual == null || matActual.isEmpty()) {
            return 0;
        }
        String puestoActual = puesto.getNombre();
        if (puestoActual == null || puestoActual.isEmpty()) {
            return 0;
        }

        // recorro transitos y cuento los previos
        int transitosPrevios = 0;
        for (int i = 0; i < transitos.size(); i++) {
            Transito t = transitos.get(i);
            String matTransito = t.getVehiculo().getMatricula();
            String puestoTransito = t.getPuesto().getNombre();
            Date fechaTransito = t.getFechaHora();
            if (matActual.equalsIgnoreCase(matTransito) && puestoActual.equalsIgnoreCase(puestoTransito)
                    && fechaTransito.equals(fecha)) {
                transitosPrevios++;
            }
        }

        // Si existe al menos 1 tránsito previo en el mismo día/puesto/vehículo
        if (transitosPrevios >= 1) {
            return this.descuento;
        }
        return 0;
    }

}
