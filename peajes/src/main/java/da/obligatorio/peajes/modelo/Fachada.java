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
  
    public Administrador loginAdministrador(String ced, String pwd) throws PeajeException {
        return sAcceso.loginAdministrador(ced, pwd);
    }

    public void logout(HttpSession sesionHttp, Administrador adm) throws PeajeException {
        sAcceso.logout(sesionHttp, adm);
    }



    //SISTEMA PROPIETARIO
    public void agregarVehiculo (Vehiculo v){
        sPropietario.agregarVehiculo(v);
    } 


    //SISTEMA PEAJE
    public void agregarCategoria(Categoria c){
        sPeaje.agregarCategoria(c);
    }

    public void agregarPuesto(Puesto p){
        s.Peaje.agregarPuesto(p);
    }


    

}
