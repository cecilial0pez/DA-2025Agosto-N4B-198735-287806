package da.obligatorio.peajes.modelo;

import java.util.ArrayList;

public class SistemaAcceso {
    
private ArrayList<Administrador> administradores = new ArrayList();
private ArrayList<Propietario> propietarios = new ArrayList();

     public Propietario loginPropietario(String nom,String pwd) throws PeajeException{
       Propietario prop =  (Propietario) login(nom, pwd, propietarios);
       if(prop==null) throw new PeajeException("Login incorrecto");
       return prop;
    }

      public Administrador loginAdministrador(String ced,String pwd) throws PeajeException{
       Administrador adm =  (Administrador) login(ced, pwd, administradores);
       if(adm==null) throw new PeajeException("Login incorrecto");
       return adm;
    }

     private Usuario login(String ced, String pwd, ArrayList lista){
        Usuario usuario;
        
        for(Object o:lista){
            usuario = (Usuario)o;
            if(usuario.getCedula().equals(ced) && usuario.getContrasenia().equals(pwd)){
                return usuario;
            }
        }
        return null;
    }
}
