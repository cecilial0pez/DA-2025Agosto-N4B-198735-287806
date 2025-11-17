package da.obligatorio.peajes.modelo;

import observador.Observable;
import observador.Observador;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

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
        if( u instanceof Propietario) {
            sPeaje.agregarPropietario((Propietario) u);
        }
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

  public void agregarBonificacion(Bonificacion b){
        sPeaje.agregarBonificacion(b);
    }

    public List<Notificacion> getNotificaciones(Propietario propietario){
        return sPeaje.getNotificaciones(propietario);
    }

    public Transito agregarTransito (String matricula, Date fechaHora, String nombrePuesto) throws PeajeException{
        return sPeaje.agregarTransito(matricula, fechaHora, nombrePuesto);
    }

    public void borrarNotificacionesPropietario(Propietario propietario){
        sPeaje.eliminarNotificaciones(propietario);
    }

    public List<Puesto> getPuestosPeaje(){
        return sPeaje.getPuestosPeaje();
    }   

    public List<Tarifa> getTarifasPuesto(String nombrePuesto) throws PeajeException{
        return sPeaje.getTarifasPuesto(nombrePuesto);
    }

    // public void AgregarAsignacionBonificacion(String nombrePuesto,String nombreBonificacion,String cedula ) throws PeajeException{
    //     try {
    //         sPeaje.AgregarAsignacionBonificacion(nombrePuesto, nombreBonificacion, cedula);
    //         this.notificar(Eventos.cambioBonificaciones);
    //     } catch (PeajeException e) {
    //         throw new PeajeException(e.getMessage());
    //     }
    // }

    // //Devuelve las notificaciones de cada propietario en el scope
    // public ArrayList<Notificacion> getNotificaciones() {
    //     return sPeaje.getNotificaciones();
    // }

}
