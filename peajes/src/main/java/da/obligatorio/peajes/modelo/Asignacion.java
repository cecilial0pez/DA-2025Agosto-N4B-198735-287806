package da.obligatorio.peajes.modelo;

import java.sql.Date;

public class Asignacion {
    private Date fechaAsignacion;
    private Bonificacion bonificacion;
    private Propietario propietario; //duda
    private Puesto puesto;

    public Asignacion(Date fechaAsignacion, Bonificacion bonificacion, Propietario propietario, Puesto puesto) {
        this.fechaAsignacion = fechaAsignacion;
        this.bonificacion = bonificacion;
        this.propietario = propietario;
        this.puesto = puesto;
    }
    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }
    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
    public Bonificacion getBonificacion() {
        return bonificacion;
    }
    public void setBonificacion(Bonificacion bonificacion) {
        this.bonificacion = bonificacion;
    }
    public Propietario getPropietario() {
        return propietario;
    }
    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
    public Puesto getPuesto() {
        return puesto;
    }
    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }
    
}
