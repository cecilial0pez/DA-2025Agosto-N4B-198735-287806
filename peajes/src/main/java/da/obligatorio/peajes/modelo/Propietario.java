package da.obligatorio.peajes.modelo;

import java.util.ArrayList;
import java.util.List;
import da.obligatorio.peajes.modelo.Estado;
import da.obligatorio.peajes.modelo.Vehiculo;
import da.obligatorio.peajes.modelo.Transito;
import da.obligatorio.peajes.modelo.Notificacion;
import java.sql.Date;
import java.time.LocalDateTime;

public class Propietario extends Usuario {

    private double saldo; 
    private double saldoMinimo ;
    private Estado estado = new Habilitado(this);
    // Listas
    private List<Vehiculo> vehiculos;
    private List<Asignacion> asignaciones;
    private List<Notificacion> notificaciones;
    private List<Transito> transitos;

    public Propietario(String contrasenia, String cedula, String nombre, Double saldo,double saldoMinimo) {
        super(contrasenia, cedula, nombre);
        this.saldo = saldo;
        this.saldoMinimo = saldoMinimo;
      
    }

    //getters y setters 
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
//Si el saldo del usuario es menor al valor de la alerta de saldo mínimo el sistema registra
 //una notificación al propietario: [Fecha y hora de la notificación] + “Tu saldo actual es
 //de $ “ + saldo actual + “ Te recomendamos hacer una recarga”;
    public boolean haySaldoSuficiente(double totalPagar) {
    if(this.saldo - totalPagar <= this.saldoMinimo){
    return false;
    }
        return true;
    }

    public double actualizarSaldo(double montoGastado) {
        this.saldo -= montoGastado;
        return this.saldo;
    }

    public void eliminarNotificaciones() {
        this.notificaciones.clear();
    }

   
    public void cambiarEstado(Estado estadoPropietario) throws PeajeException {
        this.estado = estadoPropietario;
        try{
        if (estadoPropietario.getNombre().equals("Penalizado")) {
            this.estado.penalizar();
            this.notificaciones.add(new Notificacion("Su estado ha cambiado a Penalizado."));
        }
        if (estadoPropietario.getNombre().equals("Suspendido")) {
            this.estado.suspender();
            this.notificaciones.add(new Notificacion("Su estado ha cambiado a Suspendido."));
        }
        if(estadoPropietario.getNombre().equals("Habilitado")) {
            this.estado.habilitar();
            this.notificaciones.add(new Notificacion("Su estado ha cambiado a Habilitado."));
        }
        if(estadoPropietario.getNombre().equals("Deshabilitado")) {
            this.estado.desHabilitar();
            this.notificaciones.add(new Notificacion("Su estado ha cambiado a Deshabilitado."));
        }
        }catch(Exception e){
            throw new PeajeException("Error al cambiar el estado del propietario.");
        }

    }
    
    public boolean puedeLoguearse() {
        // Delegar la lógica al objeto Estado (null-safe).
        return this.estado != null && this.estado.puedeLoguearse();
    }

    public void hacerRegistrarTransito(Transito transito) throws PeajeException {
        if (this.transitos == null) {
            this.transitos = new ArrayList<Transito>();
        }
        Puesto puesto = transito.getPuesto();
        if( hayBonificacionEnPuesto(puesto)){
            Bonificacion bonif = bonificacionporPuesto(puesto);
            transito.setNombreBonificacion(bonif.getNombre());
            Double porcentajeDescuento= bonif.getPorcentajeDescuento();
            Double aApagar=transito.calcularTotalAPagar(transito.getTarifa().getMonto(), porcentajeDescuento);
            transito.setTotalPagado(aApagar);
        }else{
            transito.setTotalPagado(transito.getTarifa().getMonto());
        }
//getTotalPagado
        if(haySaldoSuficiente(transito.getTotalPagado())==false){
            registrarNotificacion("Tu saldo actual es de $ " + this.saldo + " Te recomendamos hacer una recarga");
            throw new PeajeException("Saldo insuficiente para realizar el transito. Saldo actual: " + this.saldo);
        }else{
            actualizarSaldo(transito.getTotalPagado());
            this.transitos.add(transito);
            
            registrarNotificacion("Pasaste por el puesto " + transito.getPuesto().getNombre() + " con el vehículo " + transito.getVehiculo().getMatricula());
        }     
        
    }

    //asigna la bonificacion y genera la notificacion 
    public void asignarBonificacion(Puesto puesto, Bonificacion bonificacion)throws PeajeException {
         if (bonificacion == null || puesto == null)
            return;
        if (!this.estado.puedeLoguearse()) {
            throw new PeajeException("El propietario esta deshabilitado. No se pueden asignar bonificaciones");
        }
        if(hayBonificacionEnPuesto(puesto)){
            throw new PeajeException("Ya existe una bonificacion para ese puesto");
        }
        if (this.asignaciones == null){
            this.asignaciones = new ArrayList<>();}
        Asignacion a = new Asignacion(bonificacion, this, puesto);
        this.asignaciones.add(a);
        
        registrarNotificacion("Se ha asignado la bonificacion " + bonificacion.getNombre() + " para el puesto " + puesto.getNombre());
        
    }

    public boolean hayBonificacionEnPuesto(Puesto puesto) {
        if (puesto == null) {
            return false;
        }
        if(this.asignaciones == null || this.asignaciones.isEmpty()){ 
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

    public Bonificacion bonificacionporPuesto(Puesto puesto){
        List<Asignacion> asignaciones = this.getAsignaciones();
        for (Asignacion asignacion : asignaciones) {
            if (asignacion != null && puesto.equals(asignacion.getPuesto())) {     
                Bonificacion bon=asignacion.getBonificacion();
                return bon;
            }
        }
        return null;
    }

    public void registrarNotificacion(String mensaje) throws PeajeException {
        Notificacion notificacion = new Notificacion(mensaje);
        notificacion.validar();
        if(this.notificaciones==null){
            this.notificaciones=new ArrayList<Notificacion>();
        }
        if (notificaciones.contains(notificacion))
            throw new PeajeException("Ya existe la notificacion");

        hacerRegistrarNotificacion(notificacion);
    }

    public void hacerRegistrarNotificacion(Notificacion notificacion) {
       if (this.notificaciones==null){
        this.notificaciones=new ArrayList<Notificacion>();
       }
       this.notificaciones.add(notificacion);
    }

    public void agregarVehiculo(Vehiculo vehiculo){
        if (this.vehiculos == null) {
            this.vehiculos = new ArrayList<Vehiculo>();
        }
        this.vehiculos.add(vehiculo);
    }

}

