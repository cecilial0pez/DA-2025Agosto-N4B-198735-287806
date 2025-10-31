package da.obligatorio.peajes.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

public class SistemaAcceso {
    
private List<Administrador> administradores = new ArrayList<>();
private List<Propietario> propietarios = new ArrayList<>();

  public ArrayList<Administrador> getAdministradores() {
        return new ArrayList<>(administradores);
    }

    public ArrayList<Propietario> getPropietarios() {
        return new ArrayList<>(propietarios);
    }
    

    public void agregarUsuario(Usuario u ) {
        if(u instanceof Propietario){
            propietarios.add((Propietario)u);
        }else if (u instanceof Administrador){
            administradores.add((Administrador)u);
        }
    } //no seguras del instaceof

     public Propietario loginPropietario(String nom,String pwd) throws PeajeException{
       Propietario prop =  (Propietario) login(nom, pwd, propietarios);
       if(prop==null) throw new PeajeException("Login incorrecto");
       return prop;
    }

      // devuelve el Administrador; quien manipula la sesión es el controlador
      public Administrador loginAdministrador(String ced,String pwd) throws PeajeException{
       Administrador adm =  (Administrador) login(ced, pwd, administradores);
       if(adm==null) throw new PeajeException("Login incorrecto");
       return adm;
    }

     public Usuario login(String ced, String pwd, List<?> lista){
        Usuario usuario;
        
        for(Object o:lista){
            usuario = (Usuario)o;
            if(usuario.getCedula().equals(ced) && usuario.getContrasenia().equals(pwd)){
                return usuario;
            }
        }
        return null;
    }

    public void logout(HttpSession sesionHttp, Administrador adm) {
       // Obtener la sesión HTTP actual
        if(sesionHttp!=null){
            sesionHttp.removeAttribute("usuarioAdm");
        }
    }


}
