package da.obligatorio.peajes.modelo;

import java.sql.Date;
import java.util.List;

public class Notificacion {
    private String mensaje;
    private Date fechaHoraEnvio;
    private List<Propietario> propietarios;

    public Notificacion(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
   

    
}