package da.obligatorio.peajes.modelo;

import java.util.List;
import da.obligatorio.peajes.modelo.Estado;
import da.obligatorio.peajes.modelo.Vehiculo; 

public class Propietario extends Usuario {
  
    public String nombre;
   
    public double saldo; //hacer saldominimocomometodo Saldo mínimo para alerta: 500
    public Estado estado;
    public List<Vehiculo> vehiculos;
    public List<Notificacion> notificaciones; 
    public List<Asignacion> asignaciones; 

    public Propietario(String contrasenia, String cedula, String nombre, Double saldo, Estado estado) {
        super(contrasenia, cedula);
        this.nombre = nombre;
        
        this.saldo = saldo;
        this.estado.nombre = "Habilitado";
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

    
}
