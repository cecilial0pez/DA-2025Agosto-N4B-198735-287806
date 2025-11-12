package da.obligatorio.peajes.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import da.obligatorio.peajes.modelo.Notificacion;

public class NotificacionDTO {
    private String mensaje;
    private String fecha;

     public NotificacionDTO(Notificacion notificacion) {
        mensaje = notificacion.getMensaje();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        fecha = sdf.format(notificacion.getFechaHoraEnvio());
    }

     public static List<NotificacionDTO> listaNotificacionesDto(List<Notificacion> notificaciones) {
                
        List<NotificacionDTO> notifiDtos = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            notifiDtos.add(new NotificacionDTO(notificacion));
        }
        return notifiDtos;
    }
    

}
