package da.obligatorio.peajes.modelo;

import observador.Observable;
import observador.Observador;

import java.util.ArrayList;

import jakarta.servlet.http.HttpSession;

public class Fachada extends Observable {

    private SistemaAcceso sAcceso = new SistemaAcceso();
    private SistemaPeaje sPeaje = new SistemaPeaje();

     //SINGLETON

    private static Fachada instancia = new Fachada();

    public static Fachada getInstancia() {
        return instancia;
    }
    
    private Fachada() {
    }

     public enum Eventos{cambioBonificaciones,cambioListaNotificaciones};

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


    //Metodos para agregar datos al sistema peaje
    public void agregarCategoria(Categoria c){
        sPeaje.agregarCategoria(c);
    }

     public void agregarVehiculo (Vehiculo v){
        sPeaje.agregarVehiculo(v);
    } 

    public void agregarPuesto(Puesto p){
        sPeaje.agregarPuesto(p);
    }

     public void agregarEstado(Estado e){
        sPeaje.agregarEstado(e);
    }

   public void agregarTarifa(String nombrePuesto, String nombreCategoria, double monto) throws PeajeException {
        try {
            sPeaje.agregarTarifa(nombrePuesto, nombreCategoria, monto);
        } catch (PeajeException e) {
            throw new PeajeException(e.getMessage());
        }
    }

    // public void agregarEstado (Estado e){
    //     sPeaje.agregarEstado(e);
    // }

    public void agregarTransito (Transito t){
        sPeaje.agregarTransito(t);
    }

   

    // public void agregarNotificacion (Notificacion n){
    //     sPeaje.agregarNotificacion(n);
    // }

    // //Devuelve las notificaciones de cada propietario en el scope
    // public ArrayList<Notificacion> getNotificaciones() {
    //     return sPeaje.getNotificaciones();
    // }

}
