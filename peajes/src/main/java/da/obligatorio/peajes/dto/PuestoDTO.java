package da.obligatorio.peajes.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import da.obligatorio.peajes.modelo.Notificacion;
import da.obligatorio.peajes.modelo.Puesto;
public class PuestoDTO {
      private String nombre;


      public PuestoDTO(Puesto puesto){
         this.nombre=puesto.getNombre();
      }

      public String getNombre() {
         return nombre;
      }
         
     public static List<PuestoDTO> listaPuestoDto(List<Puesto> puestos) {
                
        List<PuestoDTO> puestoDtos = new ArrayList<>();
        for (Puesto puesto : puestos) {
            puestoDtos.add(new PuestoDTO(puesto));
        }
        return puestoDtos;
    }
    
   
}   
