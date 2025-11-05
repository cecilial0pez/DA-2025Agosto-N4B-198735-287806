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
  
    public String nombre;
   
    public double saldo; //hacer saldominimocomometodo Saldo mínimo para alerta: 500
    public Estado estado;
    public List<Vehiculo> vehiculos;
    public List<Notificacion> notificaciones; 
    public List<Asignacion> asignaciones; 
    public List<Transito> transitos;

    public Propietario(String contrasenia, String cedula, String nombre, Double saldo, Estado estado) {
        super(contrasenia, cedula);
        this.nombre = nombre;
        
        this.saldo = saldo;
        this.estado = (estado != null) ? estado : new Estado("Habilitado");
    }
    

 

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void registrarTransito(Transito transito){//crear un nuevo objeto transito y agregarlo a una lista de transitos   
        if(this.transitos == null){
            this.transitos = new ArrayList<Transito>();
        }
        this.transitos.add(transito);
    }

    public void hacerRegistrarTransito (Vehiculo vehiculo, Date fechaHora, Puesto puesto, double totalPagado){
        Transito transito = new Transito(vehiculo, fechaHora, puesto, totalPagado);
        registrarTransito(transito);
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

    public void registrarNotificacion (){
        //metodo para registrar notificacion a un propietario
    }

    public void hacerRegistrarNotificacion (){
        //metodo para hacer el registro de notificacion a un propietario
    }


}