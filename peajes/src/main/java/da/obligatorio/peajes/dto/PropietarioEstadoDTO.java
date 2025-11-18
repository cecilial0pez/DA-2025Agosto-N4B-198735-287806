package da.obligatorio.peajes.dto;

import da.obligatorio.peajes.modelo.Propietario;

public class PropietarioEstadoDTO {
     private String nombre;
    private String estado;


     public PropietarioEstadoDTO(Propietario propietario) {
        this.nombre = propietario.getNombre();
        this.estado = propietario.getEstado().getNombre();
    }
    
    public String getEstado() {
        return estado;
    }

    public String getNombre(){
        return nombre;
    }

    
}
