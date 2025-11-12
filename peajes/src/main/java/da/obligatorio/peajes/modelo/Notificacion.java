package da.obligatorio.peajes.modelo;

import java.util.Date;
import java.util.List;
import java.util.Observable;

public class Notificacion extends Observable {
    private String mensaje;
    private Date fechaHoraEnvio;
   
    public Notificacion(String mensaje) {
        this.mensaje = mensaje;
        this.fechaHoraEnvio = new Date();
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechaHoraEnvio() {
        return fechaHoraEnvio;
    }

    public void setFechaHoraEnvio(Date fechaHoraEnvio) {
        this.fechaHoraEnvio = fechaHoraEnvio;
    }

    public void validar() throws PeajeException {
        if (mensaje == null || mensaje.isEmpty()) {
            throw new PeajeException("El mensaje de la notificación no puede estar vacío.");
        }
        
    }

}