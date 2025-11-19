package da.obligatorio.peajes.modelo;

import java.util.ArrayList;
import java.util.List;
import da.obligatorio.peajes.modelo.Estado;
import da.obligatorio.peajes.modelo.Vehiculo;
import da.obligatorio.peajes.modelo.Transito;
import da.obligatorio.peajes.modelo.Notificacion;
import java.sql.Date;
import java.time.LocalDateTime;
import observador.Observable;


public class Propietario extends Usuario  {
    
    private Observable observable = new Observable();
     public enum Eventos{cambioListaTransitos,cambioEstado, cambioListaNotificaciones, cambioListaAsignaciones, cambioSaldo};

    private double saldo;
    private double saldoMinimo;
    private Estado estado = new Habilitado(this);
    // Listas
    private List<Vehiculo> vehiculos;
    private List<Asignacion> asignaciones;
    private List<Notificacion> notificaciones;
    private List<Transito> transitos;

    public Propietario(String contrasenia, String cedula, String nombre, Double saldo, double saldoMinimo) {
        super(contrasenia, cedula, nombre);
        this.saldo = saldo;
        this.saldoMinimo = saldoMinimo;
    }

    // getters y setters
    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public List<Transito> getTransitos() {
        return transitos;
    }

    public List<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    // Metodos agregados
    // agregar notificaciones, vehiculos y asignaciones
    // Si el saldo del usuario es menor al valor de la alerta de saldo mínimo el
    // sistema registra
    // una notificación al propietario: [Fecha y hora de la notificación] + “Tu
    // saldo actual es
    // de $ “ + saldo actual + “ Te recomendamos hacer una recarga”;
    public boolean haySaldoSuficiente(double totalPagar) {
        if (this.saldo - totalPagar <= this.saldoMinimo) {
            return false;
        }
        return true;
    }

    public double actualizarSaldo(double montoGastado) {
        this.saldo -= montoGastado;
        observable.avisar(Eventos.cambioSaldo);
        Fachada.getInstancia().avisar(Eventos.cambioSaldo);
        return this.saldo;
    }

    public void eliminarNotificaciones() {
        this.notificaciones.clear();
        observable.avisar(Eventos.cambioListaNotificaciones);
        Fachada.getInstancia().avisar(Eventos.cambioListaNotificaciones);
    }

    void aplicarCambioDirecto(Estado nuevoEstado) throws PeajeException {
        if (nuevoEstado == null) {
            throw new PeajeException("El nuevo estado no puede ser nulo.");
        }
        if (this.estado != null && this.estado.getNombre().equals(nuevoEstado.getNombre())) {
            throw new PeajeException("El estado es el mismo que el actual: " + this.estado.getNombre());
        }
        this.estado = nuevoEstado;
        Notificacion notificacion = new Notificacion(
                "Se ha cambiado tu estado en el sistema. Tu estado actual es " + nuevoEstado.getNombre());
        notificacion.validar();
        hacerRegistrarNotificacion(notificacion);
        observable.avisar(Eventos.cambioEstado);
        Fachada.getInstancia().avisar(Eventos.cambioEstado);
    }

    
    public void cambiarEstado(Estado nuevoEstado) throws PeajeException {

        if (nuevoEstado == null) {
            throw new PeajeException("El nuevo estado no puede ser nulo.");
        }
        if (this.estado.getNombre().equals(nuevoEstado.getNombre())) {
            throw new PeajeException("El estado es el mismo que el actual: " + this.estado.getNombre());
        }

        String nombreDestino = nuevoEstado.getNombre();

        switch (nombreDestino) {
            case "Penalizado":
                this.estado.penalizar();
                break;
            case "Suspendido":
                this.estado.suspender();
                break;
            case "Habilitado":
                this.estado.habilitar();
                break;
            case "Deshabilitado":
                this.estado.desHabilitar();
                break;
            default:
                throw new PeajeException("Estado no soportado: " + nombreDestino);
        }
    }

    /**
     * El sistema cambia el estado del propietario y registra una notificación al
     * propietario:
     * [Fecha y hora de la notificación] + “Se ha cambiado tu estado en el sistema.
     * Tu estado actual * es “ + nombre del estado actual. (Esta notificación
     * siempre se registra, sin
     * importar si el estado
     * * actual o el anterior permiten registrar notificaciones)
     */

    public boolean puedeLoguearse() {
        // Delegar la lógica al objeto Estado (null-safe).
        return this.estado != null && this.estado.puedeLoguearse();
    }

    public void hacerRegistrarTransito(Transito transito) throws PeajeException {
        if (this.transitos == null) {
            this.transitos = new ArrayList<Transito>();
        }
        Puesto puesto = transito.getPuesto();
        if (hayBonificacionEnPuesto(puesto)) {
            Bonificacion bonif = bonificacionporPuesto(puesto);
            transito.setNombreBonificacion(bonif.getNombre());
            Double porcentajeDescuento = bonif.getPorcentajeDescuento();
            Double aApagar = transito.calcularTotalAPagar(transito.getTarifa().getMonto(), porcentajeDescuento);
            transito.setTotalPagado(aApagar);
        } else {
            transito.setTotalPagado(transito.getTarifa().getMonto());
        }
        // getTotalPagado
        if (haySaldoSuficiente(transito.getTotalPagado()) == false) {
            registrarNotificacion("Tu saldo actual es de $ " + this.saldo + " Te recomendamos hacer una recarga");
            throw new PeajeException("Saldo insuficiente para realizar el transito. Saldo actual: " + this.saldo);
        } else {
            actualizarSaldo(transito.getTotalPagado());
            this.transitos.add(transito);
            observable.avisar(Eventos.cambioListaTransitos);
             Fachada.getInstancia().avisar(Eventos.cambioListaTransitos);
            registrarNotificacion("Pasaste por el puesto " + transito.getPuesto().getNombre() + " con el vehículo "
                    + transito.getVehiculo().getMatricula());
        }

    }

    // asigna la bonificacion y genera la notificacion
    public void asignarBonificacion(Puesto puesto, Bonificacion bonificacion) throws PeajeException {
        if (bonificacion == null || puesto == null)
            return;
        if (!this.estado.puedeLoguearse()) {
            throw new PeajeException("El propietario esta deshabilitado. No se pueden asignar bonificaciones");
        }
        if (hayBonificacionEnPuesto(puesto)) {
            throw new PeajeException("Ya existe una bonificacion para ese puesto");
        }
        if (this.asignaciones == null) {
            this.asignaciones = new ArrayList<>();
        }
        Asignacion a = new Asignacion(bonificacion, this, puesto);
        this.asignaciones.add(a);

        registrarNotificacion(
                "Se ha asignado la bonificacion " + bonificacion.getNombre() + " para el puesto " + puesto.getNombre());
        observable.avisar(Eventos.cambioListaAsignaciones);
        Fachada.getInstancia().avisar(Eventos.cambioListaAsignaciones);

    }

    public boolean hayBonificacionEnPuesto(Puesto puesto) {
        if (puesto == null) {
            return false;
        }
        if (this.asignaciones == null || this.asignaciones.isEmpty()) {
            return false;
        }
        List<Asignacion> asignaciones = this.getAsignaciones();
        for (Asignacion asignacion : asignaciones) {
            if (asignacion != null && puesto.equals(asignacion.getPuesto())) {
                return true;
            }
        }
        return false;
    }

    public Bonificacion bonificacionporPuesto(Puesto puesto) {
        List<Asignacion> asignaciones = this.getAsignaciones();
        for (Asignacion asignacion : asignaciones) {
            if (asignacion != null && puesto.equals(asignacion.getPuesto())) {
                Bonificacion bon = asignacion.getBonificacion();
                return bon;
            }
        }
        return null;
    }

    public void registrarNotificacion(String mensaje) throws PeajeException {
        Notificacion notificacion = new Notificacion(mensaje);
        notificacion.validar();
        if (this.notificaciones == null) {
            this.notificaciones = new ArrayList<Notificacion>();
        }
        if (notificaciones.contains(notificacion))
            throw new PeajeException("Ya existe la notificacion");

        hacerRegistrarNotificacion(notificacion);
        
    }

    public void hacerRegistrarNotificacion(Notificacion notificacion) {
        if (this.notificaciones == null) {
            this.notificaciones = new ArrayList<Notificacion>();
        }
        this.notificaciones.add(notificacion);
        observable.avisar(Eventos.cambioListaNotificaciones);
        Fachada.getInstancia().avisar(Eventos.cambioListaNotificaciones);
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        if (this.vehiculos == null) {
            this.vehiculos = new ArrayList<Vehiculo>();
        }
        this.vehiculos.add(vehiculo);
    }

     public void agregarObservador(observador.Observador obs) {
         observable.agregarObservador(obs); 
    }

    public void quitarObservador(observador.Observador obs) {
         observable.quitarObservador(obs); 
    }

   

}
