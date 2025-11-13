package da.obligatorio.peajes.modelo;

import java.util.List;
import da.obligatorio.peajes.modelo.Tarifa;
import da.obligatorio.peajes.modelo.Transito;

public class Puesto {
    private String nombre;
    private String direccion;
    private List<Tarifa> tarifasDePuesto;
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
        return tarifasDePuesto;
    }

    public void setTarifa(List<Tarifa> tarifasDePuesto) {
        this.tarifasDePuesto = tarifasDePuesto;
    }

    // Metodos agregados
    public boolean verificarPuesto(Puesto unPuesto) {
        if (unPuesto != null) {
            return true;
        }
        return false;
    }
}
