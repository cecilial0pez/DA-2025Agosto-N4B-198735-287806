package da.obligatorio.peajes.modelo;

import jakarta.servlet.http.HttpSession;

public class Fachada {

    private SistemaAcceso sAcceso = new SistemaAcceso();
    private SistemaPropietario sPropietario = new SistemaPropietario();
    private SistemaPeaje sPeaje = new SistemaPeaje();

     //SINGLETON

    private static Fachada instancia = new Fachada();

    public static Fachada getInstancia() {
        return instancia;
    }
    
    private Fachada() {
    }

    //SISTEMA ACCESO

     public void agregarUsuario(Usuario u ) {
        sAcceso.agregarUsuario(u);
    }

  
      public Propietario loginPropietario(String ced, String pwd) throws PeajeException {
        return sAcceso.loginPropietario(ced, pwd);
    }


      // la fachada NO debe recibir ni manipular HttpSession; devuelve el Administrador
      public Administrador loginAdministrador(String ced, String pwd) throws PeajeException {
        return sAcceso.loginAdministrador(ced, pwd);
    }

    public void logout(HttpSession sesionHttp, Administrador adm) throws PeajeException {
        sAcceso.logout(sesionHttp, adm);
    }



    //SISTEMA PROPIETARIO

    //SISTEMA PEAJE

}
