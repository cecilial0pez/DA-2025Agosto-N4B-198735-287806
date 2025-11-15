package da.obligatorio.peajes.modelo;

import java.util.Date;
import java.util.Calendar;
import java.time.LocalDateTime;

import da.obligatorio.peajes.modelo.Puesto;
import da.obligatorio.peajes.modelo.Vehiculo;
import da.obligatorio.peajes.modelo.Tarifa;

public class Transito {
    private Vehiculo vehiculo;
    private Date fechaHora;
    private Puesto puesto;
    private Tarifa tarifa;
    private double totalPagado;

    public Transito(Vehiculo vehiculo, Date fechaHora, Puesto puesto, Tarifa tarifa) {
        this.vehiculo = vehiculo;
        this.fechaHora = fechaHora;
        this.puesto = puesto;
        this.tarifa = tarifa;
    }
    
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    public Date getFechaHora() {
        return fechaHora;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }
    
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
    public Puesto getPuesto() {
        return puesto;
    }
    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }
    public double getTotalPagado() {
        return totalPagado;
    }
    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }

    //Metodos agregados
    public double calcularTotalAPagar(double tarifa, double descuento){
        return tarifa - (tarifa * descuento);
    }

    public boolean VerificarFecha (LocalDateTime fecha){
        boolean ok = false;
      LocalDateTime fechaActual = LocalDateTime.now();
        if(fecha.isAfter(fechaActual) || fecha.isEqual(fechaActual)){
            ok = true;
        }
        return ok;
    }

  
}   
