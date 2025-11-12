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
  
    private double saldo; //hacer saldominimocomometodo Saldo mínimo para alerta: 500
    private Estado estado=new Habilitado(this);
    private List<Vehiculo> vehiculos;
    private List<Notificacion> notificaciones; 
    private List<Asignacion> asignaciones; 
    private List<Notificacion> notifs;
    private List<Transito> transitos;

    public Propietario(String contrasenia, String cedula, String nombre, Double saldo) {
        super(contrasenia,cedula,nombre);
        this.saldo = saldo;   
    }
    


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

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

  
    //Metodos agregados 
    // agregar notificaciones, vehiculos y asignaciones
      
    
   
    public boolean haySaldoMinimo(){
        return this.saldo >= 500;
    }   

    public double actualizarSaldo(double montoGastado){
        return this.saldo - montoGastado;
    }

    public void eliminarNotificaciones(){
        this.notificaciones.clear();
    }

    //metodos que nos dio el profe

    public void cambiarEstado(Estado estadoPropietario){
        this.estado = estadoPropietario;
        //meter ifs necesarios
    }

    public boolean puedeLoguearse (){
        return this.estado.getNombre().equals("Habilitado");
        //CHEQUEAR SI ESTA BIEN
    }



     public void registrarTransito (Vehiculo vehiculo, Date fechaHora, Puesto puesto, double totalPagado){
         if(this.transitos == null){
             this.transitos = new ArrayList<Transito>();
         }
       Transito transito=  new Transito( fechaHora, vehiculo, puesto, this, totalPagado);
        hacerRegistrarTransito (transito);
        
     }

     public void hacerRegistrarTransito (Transito transito){
       if(this.transitos == null){
         this.transitos = new ArrayList<Transito>();
        }
       this.transitos.add(transito);
        
    }

    public void asignarBonificacion (){
        //metodo para asignar bonificacion a un propietario
    }

    public void hacerAsignarBonificacion (){
        //metodo para hacer la asignacion de bonificacion a un propietario
    }

    public void aplicarDescuento (){
        //metodo para aplicar descuento a un propietario
    }

    public void hacerAplicarDescuento (){
        //metodo para hacer la aplicacion de descuento a un propietario
    }

    public void registrarNotificacion (String mensaje) throws PeajeException{
        Notificacion notificacion = new Notificacion(mensaje);
        notificacion.validar();
        if(notificaciones.contains(notificacion)) throw new PeajeException("Ya existe la notificacion");

        hacerRegistrarNotificacion (notificacion);
    }

    public void hacerRegistrarNotificacion (Notificacion notificacion){
        notificaciones.add(notificacion);
    }


}