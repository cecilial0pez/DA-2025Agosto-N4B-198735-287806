package da.obligatorio.peajes.modelo;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SistemaPeaje {
    private ArrayList<Puesto> puestosPeaje = new ArrayList();
    private ArrayList<Categoria> categorias = new ArrayList();
    private ArrayList<Estado> estados = new ArrayList();
    private ArrayList<Vehiculo> vehiculos = new ArrayList();
    private ArrayList<Propietario> propietarios = new ArrayList();
    private ArrayList<Bonificacion> bonificaciones = new ArrayList();

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public ArrayList<Puesto> getPuestosPeaje() {
        return puestosPeaje;
    }

    //Métodos para agregar datos. 
    public void agregarEstado(Estado e) {
     estados.add(e);
     }

    public void agregarPropietario(Propietario prop) {
        propietarios.add(prop);
    }

    public void agregarCategoria(Categoria c) {
        categorias.add(c);
    }

    public void agregarPuesto(Puesto p) {
        puestosPeaje.add(p);
    }

  
    public void agregarVehiculo(Vehiculo v) {
        vehiculos.add(v);
    }  


    public void agregarTarifa(String nombrePuesto, String nombreCategoria, double monto) throws PeajeException {
        Puesto puestoAbuscar=buscarPuesto(nombrePuesto);    
        Categoria categoriaAbuscar=buscarCategoria(nombreCategoria);
        if(puestoAbuscar != null && categoriaAbuscar != null){
            Tarifa tarifa = new Tarifa(monto,categoriaAbuscar);
            puestoAbuscar.getTarifas().add(tarifa);
        }else{
            throw new PeajeException("El puesto buscado o la cateogria buscada no existen");
        }
    }


    public void AgregarAsignacionBonificacion(String nombrePuesto,String nombreBonificacion,String cedula ) throws PeajeException{
        Puesto puestoAbuscar=buscarPuesto(nombrePuesto);    
        Propietario propietarioAbuscar=buscarPropietario(cedula);
        Bonificacion bonificacionAbuscar=buscarBonificacion(nombreBonificacion);
        if(puestoAbuscar != null && propietarioAbuscar != null && bonificacionAbuscar != null){
            if(propietarioAbuscar.puedeBonificarse(puestoAbuscar)){
                propietarioAbuscar.asignarBonificacion(puestoAbuscar, bonificacionAbuscar);
            }
            else{
                throw new PeajeException("Ya existe una bonificacion para ese puesto");
            }
        }
    }

    //ATENCION !!!!!!!!!!!1 este metodo recontra sirve terminalo!!!!!!!!!!
     public void agregarTransito(String matricula, Date fechaHora, String nombrePuesto) throws PeajeException{
       Vehiculo v=buscarVehiculo(matricula);
        Propietario propietario= v.getPropietario();
       Puesto p= buscarPuesto(nombrePuesto);
        Tarifa t= p.TarifaPorCategoria(v.getCategoria());
        if(SePuedeHacerTransito( v, propietario, p, t)){
            Transito transito=new Transito(v, fechaHora, p, t);
            //aplicar bonificacion
            intentarBonificar (transito);
            //calcular total pagado esto hay que cambairlo URGENTE 
            double montoBase= t.getMonto();
            double descuento=0.0;
            Bonificacion bonifSeleccionada= seleccionarBonificacion(propietario, v, p, fechaHora);
            if(bonifSeleccionada != null){
                descuento= bonifSeleccionada.calcularBonificacion(propietario, v, p, fechaHora);
                transito.setNombreBonificacion(bonifSeleccionada.getNombre());
            }
            double totalAPagar= montoBase - descuento;
            transito.setTotalPagado(totalAPagar);
            //registrar transito en el propietario
            propietario.hacerRegistrarTransito(transito);
            v.incrementarCantidadTransitos();
            p.agregarTransito(transito);
        } 
     }
     //linea 94 hacer cantidad de transitos por puesto

     public boolean SePuedeHacerTransito(Vehiculo v, Propietario prop, Puesto p, Tarifa t) throws PeajeException
     {
       if(v==null){
            throw new PeajeException("No existe vehiculo con esa matricula");
        }else if(prop==null){
            throw new PeajeException("No existe un propietario con esa cedula");
        }else if(p==null){
            throw new PeajeException("No existe un puesto con ese nombre");
        } else if(t==null){
            throw new PeajeException("No existe tarifa para esa categoria en ese puesto");
        }else if(prop.getEstado().getNombre().equals("Suspendido")||prop.getEstado().getNombre().equals("Deshabilitado" )){
            throw new PeajeException("El propietario no puede realizar transitos, ya que su estado es "+ prop.getEstado().getNombre());
        }else{
            return true;
        }
     }


    //Metodos de busqueda
     private Puesto buscarPuesto(String nombrePuesto) throws PeajeException {
        for (Puesto p : puestosPeaje) {
            if (p.getNombre().equals(nombrePuesto)) {
                return p;
            }
        }
        throw new PeajeException("No existe un puesto con ese nombre");
    }
    
    private Categoria buscarCategoria(String nombreCategoria) throws PeajeException {
        for (Categoria c : categorias) {
            if (c.getNombre().equals(nombreCategoria)) {
                return c;
            }
        }
        throw new PeajeException("No existe una categoría con ese nombre ");
    }

    private Propietario buscarPropietario(String cedula) throws PeajeException {
        for (Propietario p : propietarios) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        throw new PeajeException("No existe un propietario con esa cédula ");
    }

    private Bonificacion buscarBonificacion(String nombre) throws PeajeException{
        for (Bonificacion b:bonificaciones){
            if(b.getNombre().equals(nombre)){
                return b;
            }
        }
        throw new PeajeException("No existe una bonificacion con ese nombre");
    }

    private Vehiculo buscarVehiculo(String matricula) throws PeajeException{
        for (Vehiculo v:vehiculos){
            if(v.getMatricula().equals(matricula)){
                return v;
            }
        }
        throw new PeajeException ("No existe vehiculo con esa matricula");
    }
 // Metodos Auxiliares
    private Bonificacion seleccionarBonificacion(Propietario propietario,
                                                 Vehiculo vehiculo,
                                                 Puesto puesto,
                                                 java.util.Date fecha) {
        Bonificacion elegida = null;
        double mejorMonto = 0.0;

        for (Bonificacion bonif : bonificaciones) {
            double monto = bonif.calcularBonificacion(propietario, vehiculo, puesto, fecha );
            if (monto > mejorMonto) {
                mejorMonto = monto;
                elegida = bonif;
            }
        }
        return elegida;
    }

    private void intentarBonificar(Transito transito) throws PeajeException {
        Propietario propietario = transito.getVehiculo().getPropietario();
        Puesto puesto = transito.getPuesto();
        if (!propietario.puedeBonificarse(puesto)) {
            return;
        }
        Bonificacion bonif = seleccionarBonificacion(propietario,
                                                     transito.getVehiculo(),
                                                     puesto,
                                                     transito.getFechaHora());
        if (bonif != null) {
            
            propietario.asignarBonificacion(puesto, bonif);
        }
    }

    public void eliminarNotificaciones(Propietario propietario){
        if(propietario != null) {
            propietario.getNotificaciones().clear();
        }

    }

    public List<Notificacion> getNotificaciones(Propietario propietario){
        if(propietario != null) {
            return propietario.getNotificaciones();
        }
        return new ArrayList<>();
    }

}
