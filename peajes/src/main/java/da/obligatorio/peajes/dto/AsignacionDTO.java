package da.obligatorio.peajes.dto;
import da.obligatorio.peajes.modelo.Asignacion;

public class AsignacionDTO {
    private String bonificacion;
    private String puesto;
    private String fechayhora;

 public AsignacionDTO(Asignacion asignacion){
    this.bonificacion=asignacion.getBonificacion().getNombre();
 }

}
