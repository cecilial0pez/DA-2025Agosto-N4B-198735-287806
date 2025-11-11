package da.obligatorio.peajes.modelo;

import java.util.ArrayList;


    public class Administrador extends Usuario {
   private String nombre;

   private ArrayList<Estado> estados;

    public Administrador(String nombre, String contrasenia,String cedula) {
        super(contrasenia, cedula);
        this.nombre = nombre;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

  
}
