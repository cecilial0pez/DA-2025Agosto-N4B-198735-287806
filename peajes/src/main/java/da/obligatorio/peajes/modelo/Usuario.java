package da.obligatorio.peajes.modelo;

import java.util.ArrayList;

public abstract class Usuario {
    private String contrasenia;
    private String cedula;
    private ArrayList<Propietario> propietarios = new ArrayList();
    private ArrayList<Administrador> administradores = new ArrayList();


     public Usuario(String contrasenia, String cedula) {
        this.contrasenia = contrasenia;
        this.cedula = cedula;
    }

     public boolean verificarCedula(String cedula){
        boolean ok = false;
        if (cedula !=null){
            int digitos = cedula.length();
            String numeros = "0123456789";
            boolean soloNumeros = true;
            cedula = cedula.toLowerCase();
            for (int x=0;x<cedula.length()&&soloNumeros;x++){
                String d = cedula.charAt(x)+"";
                if (!numeros.contains(d)){
                    soloNumeros = false;
                }
            }
           
            if (soloNumeros && digitos>=6&&digitos<8){
                ok = true;
            }    
        }
        return ok;        
    
    }

     public String getContrasenia() {
         return contrasenia;
     }




     public String getCedula() {
         return cedula;
     }
// verificar que tenga setters 
    public boolean setCedula(String unaCedula) {
       if(verificarCedula(unaCedula)){
            cedula = unaCedula;
            return true;
       }return false;
       
    }
}
