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
    private Estado estado ;
    // Listas
    private List<Vehiculo> vehiculos;
    private List<Asignacion> asignaciones;
    private List<Notificacion> notificaciones;
    private List<Transito> transitos;

    public Propietario(String contrasenia, String cedula, String nombre, Double saldo,double saldoMinimo, Estado estado) {
        super(contrasenia, cedula, nombre);
        this.saldo = saldo;
        this.saldoMinimo = saldoMinimo;
        this.estado = estado;
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

    public boolean haySaldoMinimo() {
        return this.saldo >= 500;
    }

    public double actualizarSaldo(double montoGastado) {
        return this.saldo - montoGastado;
    }

    public void eliminarNotificaciones() {
        this.notificaciones.clear();
    }

   
    // public void cambiarEstado(Estado estadoPropietario) throws PeajeException {
    //     this.estado = estadoPropietario;
    //     // para cambiar el estado del propietario

    //     if (estadoPropietario.getNombre().equals("Penalizado")) {
    //         this.estado.penalizar();
    //         this.notificaciones.add(new Notificacion("Su estado ha cambiado a Penalizado."));
    //     }
    //     if (estadoPropietario.getNombre().equals("Suspendido")) {
    //         this.estado.suspender();
    //         this.notificaciones.add(new Notificacion("Su estado ha cambiado a Suspendido."));
    //     if(estadoPropietario.getNombre().equals("Habilitado")) {
    //         this.estado.habilitar();
    //         this.notificaciones.add(new Notificacion("Su estado ha cambiado a Habilitado."));
    //     }
    //     if(estadoPropietario.getNombre().equals("Deshabilitado")) {
    //         this.estado.desHabilitar();
    //         this.notificaciones.add(new Notificacion("Su estado ha cambiado a Deshabilitado."));
    //     }}
// otros métodos prof
    public boolean puedeLoguearse() {
        // Delegar la lógica al objeto Estado (null-safe).
        return this.estado != null && this.estado.puedeLoguearse();
    }

    // public void registrarTransito(Vehiculo vehiculo, Date fechaHora, Puesto puesto, double totalPagado) {
    //     if (this.transitos == null) {
    //         this.transitos = new ArrayList<Transito>();
    //     }
    //     Transito transito = new Transito(fechaHora, vehiculo, puesto, this, totalPagado);
    //     hacerRegistrarTransito(transito);

    // }

    public void hacerRegistrarTransito(Transito transito) {
        if (this.transitos == null) {
            this.transitos = new ArrayList<Transito>();
        }
        this.transitos.add(transito);

    }

    //asigna la bonificacion y genera la notificacion 
    public void asignarBonificacion(Puesto puesto, Bonificacion bonificacion)throws PeajeException {
         if (bonificacion == null || puesto == null)
            return;
        if (!this.estado.puedeLoguearse()) {
            throw new PeajeException("El propietario esta deshabilitado. No se pueden asignar bonificaciones");
        }
        if (this.asignaciones == null)
            this.asignaciones = new ArrayList<>();
        Asignacion a = new Asignacion(bonificacion, this, puesto);
        this.asignaciones.add(a); 
        registrarNotificacion("Se ha asignado la bonificacion " + bonificacion.getNombre() + " para el puesto " + puesto.getNombre());
       
    }

    public boolean puedeBonificarse(Puesto puesto) {
        if (puesto == null) {
            return false;
        }
        if (asignaciones == null || asignaciones.isEmpty()) {
            return true;
        }
        for (Asignacion asignacion : asignaciones) {
            if (asignacion != null && puesto.equals(asignacion.getPuesto())) {
                return false;
            }
        }
        return true;
    }

    public void aplicarDescuento(Transito transito) {
        // metodo para aplicar descuento a un propietario
    }

    public void hacerAplicarDescuento() {
        // metodo para hacer la aplicacion de descuento a un propietario
    }

    public void registrarNotificacion(String mensaje) throws PeajeException {
        Notificacion notificacion = new Notificacion(mensaje);
        notificacion.validar();
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

