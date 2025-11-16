package da.obligatorio.peajes.dto;
import da.obligatorio.peajes.modelo.Asignacion;

import java.util.Date;

public class AsignacionDTO {
    private String bonificacion;
    private String puesto;
    private String fechayhora;

 public AsignacionDTO(Asignacion asignacion){
    this.bonificacion=asignacion.getBonificacion().getNombre();
   this.puesto=asignacion.getPuesto().getNombre();
    this.fechayhora=asignacion.getFechaAsignacion().toString();
 }

      public String getBonificacion() {
         return bonificacion;
      }
   
      public String getPuesto() {
         return puesto;
      }
   
      public String getFechayhora() {
         return fechayhora;
      }

}
