package da.obligatorio.peajes.modelo;


import java.util.List;
import da.obligatorio.peajes.modelo.Tarifa;
import da.obligatorio.peajes.modelo.Transito;

public class Puesto {
    private String nombre;
    private String direccion;
    private List<Tarifa> tarifa;
    // private List<Transito> transito;


    
    public Puesto(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public List<Tarifa> getTarifa() {
        return tarifa;
    }
    public void setTarifa(List<Tarifa> tarifa) {
        this.tarifa = tarifa;
    }   
    
//Metodos agregados
      public boolean verificarPuesto(Puesto unPuesto){
       if(unPuesto != null){
        return true;
       } return false;
    }
}
