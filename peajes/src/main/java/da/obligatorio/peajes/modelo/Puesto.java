package da.obligatorio.peajes.modelo;

import java.util.ArrayList;
import java.util.List;
import da.obligatorio.peajes.modelo.Tarifa;
import da.obligatorio.peajes.modelo.Transito;

public class Puesto {
    private String nombre;
    private String direccion;
    private List<Tarifa> tarifasDePuesto;
     private List<Transito> transitos;

    public Puesto(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.tarifasDePuesto = new ArrayList<>();
        this.transitos = new ArrayList<>();
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

    public List<Tarifa> getTarifas() {
        return tarifasDePuesto;
    }

    public List<Transito> getTransitos() {
        return transitos;
    }

    public void setTarifas(List<Tarifa> tarifasDePuesto) {
        this.tarifasDePuesto = tarifasDePuesto;
    }


    // Metodos agregados
    public void agregarTransito(Transito transito) {
        transitos.add(transito);
    }   

    public Tarifa TarifaPorCategoria(Categoria categoria) throws PeajeException {
        for (Tarifa t : tarifasDePuesto) {
            if (t.getCategoria().getNombre().equals(categoria.getNombre())) {
                return t;
            }
        }
        throw new PeajeException("No hay tarifa para esa categoria en este puesto");
    }
}
