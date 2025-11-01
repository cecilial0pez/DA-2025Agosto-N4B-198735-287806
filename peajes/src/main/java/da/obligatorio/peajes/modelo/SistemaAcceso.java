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
    } 

     public Propietario loginPropietario(String ced, String pwd) throws PeajeException {
        Propietario prop = (Propietario) login(ced, pwd, propietarios);
        if (prop == null) {
            throw new PeajeException("Login incorrecto");
        }
        return prop;
    }

    public Administrador loginAdministrador(String ced, String pwd) throws PeajeException {
        Administrador adm = (Administrador) login(ced, pwd, administradores);
        if (adm == null) {
            throw new PeajeException("Login incorrecto");
        }
        return adm;
    }

    private Usuario login(String ced, String pwd, List<? extends Usuario> lista) {
        if (ced == null || pwd == null) return null;
        String cedTrim = ced.trim();
        String pwdTrim = pwd.trim();
        for (Usuario usuario : lista) {
            if (usuario != null
                    && cedTrim.equals(usuario.getCedula())
                    && pwdTrim.equals(usuario.getContrasenia())) {
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
