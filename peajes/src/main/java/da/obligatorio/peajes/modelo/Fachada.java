package da.obligatorio.peajes.modelo;

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

      public Propietario loginPropietario(String ced, String pwd) throws PeajeException {
        return sAcceso.loginPropietario(ced, pwd);
    }


      public Administrador loginAdministrador(String ced, String pwd) throws PeajeException {
        return sAcceso.loginAdministrador(ced, pwd);
    }   

    //SISTEMA PROPIETARIO

    //SISTEMA PEAJE

}
