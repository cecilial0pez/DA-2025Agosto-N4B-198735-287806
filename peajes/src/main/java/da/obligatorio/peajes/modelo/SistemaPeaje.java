package da.obligatorio.peajes.modelo;

import java.lang.reflect.Array;
import java.util.Date;
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

    public ArrayList<Bonificacion>getBonificaciones(){
        return bonificaciones;
    }

    

    //Métodos para agregar datos. 
    public void agregarEstado(Estado e) {
     estados.add(e);
     }

    public void agregarPropietario(Propietario prop) {
        if(this.propietarios==null){
            this.propietarios=new ArrayList<>();
        }
        propietarios.add(prop);
    }

    public void agregarCategoria(Categoria c) {
        if(this.categorias==null){
            this.categorias=new ArrayList<>();
        }
        categorias.add(c);
    }

    public void agregarPuesto(Puesto p) {
        if(this.puestosPeaje==null){
            this.puestosPeaje=new ArrayList<>();
        }
        puestosPeaje.add(p);
    }

    public void agregarBonificacion(Bonificacion b) {
        if(this.bonificaciones==null){
            this.bonificaciones=new ArrayList<>();
        }
        bonificaciones.add(b);
    }
  
    public void agregarVehiculo(Vehiculo v) {
        if(this.vehiculos==null){
            this.vehiculos=new ArrayList<>();
        }
        vehiculos.add(v);
       Propietario prop = v.getPropietario();
       prop.agregarVehiculo(v);
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
     public Transito agregarTransito(String matricula, Date fechaHora, String nombrePuesto) throws PeajeException{
       Vehiculo v=buscarVehiculo(matricula);
        Propietario propietario= v.getPropietario();
        Puesto p= buscarPuesto(nombrePuesto);
        Tarifa t= p.TarifaPorCategoria(v.getCategoria()); //repasar para defensa 
        if(SePuedeHacerTransito( v, propietario, p, t)){
            Transito transito=new Transito(v, fechaHora, p, t);
            //aplicar bonificacion
            intentarBonificar (v,p,fechaHora);
            Bonificacion bonifSeleccionada= seleccionarBonificacion(propietario, v, p, fechaHora);
            if(bonifSeleccionada != null){
                double montoPagar= bonifSeleccionada.calcularBonificacion(propietario, v, p, fechaHora);
                transito.setNombreBonificacion(bonifSeleccionada.getNombre());
                transito.setTotalPagado(montoPagar);
            }else{
                
            }
            transito.setTotalPagado(totalAPagar); //esta mal
            //registrar transito en el propietario
            propietario.hacerRegistrarTransito(transito);
            v.incrementarCantidadTransitos();
            p.agregarTransito(transito);
            return transito;
        }else{
            return null;
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

    //  public Transito agregarTransito(String matricula, Date fechaHora, String nombrePuesto) throws PeajeException{
    //    Vehiculo v=buscarVehiculo(matricula);
    //     Propietario propietario= v.getPropietario();
    //    Puesto p= buscarPuesto(nombrePuesto);
    //     Tarifa t= p.TarifaPorCategoria(v.getCategoria()); //repasar para defensa 
    //     if(SePuedeHacerTransito( v, propietario, p, t)){
    //         Transito transito=new Transito(v, fechaHora, p, t);
    //         //aplicar bonificacion
    //         intentarBonificar (v,p,fechaHora);
    //         //calcular total pagado esto hay que cambairlo URGENTE 
    //         double montoBase= t.getMonto(); //mandar a propietario que calcule total pagado
    //         double descuento=0.0;
    //         Bonificacion bonifSeleccionada= seleccionarBonificacion(propietario, v, p, fechaHora);
    //         if(bonifSeleccionada != null){
    //             descuento= bonifSeleccionada.calcularBonificacion(propietario, v, p, fechaHora);
    //             //ACA ES DONDE CON EL MONTO DEL DESCUENTO SE LO MANDO AL PROPIETARIO PARA QUE 
    //             // DESCUENTE SU SALFO Y SI NO TIENE PARA PAGAR SALGA UNA ALERTA DE SALDO INSUFICIENTE O MINIMO
    //             transito.setNombreBonificacion(bonifSeleccionada.getNombre());
    //         }
    //         double totalAPagar= montoBase - descuento; //esta MAL asignado
    //         transito.setTotalPagado(totalAPagar); //esta mal
    //         //registrar transito en el propietario
    //         propietario.hacerRegistrarTransito(transito);
    //         v.incrementarCantidadTransitos();
    //         p.agregarTransito(transito);
    //         return transito;
    //     }else{
    //         return null;
    //     } 
        
    //  }
 
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
        double mejorMonto = 0.0; //mejor monto de bonificacion 

        for (Bonificacion bonif : bonificaciones) {
            double monto = bonif.calcularBonificacion(propietario, vehiculo, puesto, fecha );
            if (monto > mejorMonto) {
                mejorMonto = monto;
                elegida = bonif;
            }
        }
        return elegida;
    } //repasar defensa 

    private void intentarBonificar(Vehiculo vehiculo, Puesto puesto,Date fechayhora) throws PeajeException {
        Propietario propietario = vehiculo.getPropietario();
        if (!propietario.puedeBonificarse(puesto)) {
            return;
        }
        Bonificacion bonif = seleccionarBonificacion(propietario,
                                                     vehiculo,
                                                     puesto,
                                                     fechayhora);
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

    public List<Tarifa> getTarifasPuesto(String nombrePuesto) throws PeajeException{
        Puesto puestoAbuscar=buscarPuesto(nombrePuesto);    
        if(puestoAbuscar != null){
            return puestoAbuscar.getTarifas();
        }else{
            throw new PeajeException("El puesto buscado no existe");
        }
    }

}
