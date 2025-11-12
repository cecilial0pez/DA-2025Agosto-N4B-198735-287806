package da.obligatorio.peajes.modelo;


import java.util.Date;
import java.util.Calendar;

public class DatosPrueba {
    
    public static void cargar(){
        
    Fachada fachada = Fachada.getInstancia();
       
    //Datos prueba  usuarios  
    Administrador a1= new Administrador("Admin1", "admin123", "1234567");
    Administrador a2= new Administrador("Admin2", "admin456", "8765432");
    Propietario p1= new Propietario("prop123", "1122334", "Propietario1",5000.0);
    
    //Datos prueba Categoria 
    Categoria c1= new Categoria("Automovil"); 
    Categoria c2= new Categoria("Moto");
    Categoria c3= new Categoria("Camion");
    Categoria c4= new Categoria("Bus");

    // Datos prueba Vehiculos
    Vehiculo v1= new Vehiculo("ABC1234", c1, "Toyota Corolla", "Rojo");
    Vehiculo v2= new Vehiculo("DEF5678", c2, "Honda CBR500R", "Azul");
    Vehiculo v3= new Vehiculo("GHI9012", c3, "Ford F-150", "Negro");
    Vehiculo v4= new Vehiculo("JKL3456", c4, "Mercedes-Benz Sprinter", "Blanco");
    
    //Datos prueba Tarifa 
    Tarifa t1= new Tarifa(100.0);
    Tarifa t2= new Tarifa(50.0);
    Tarifa t3= new Tarifa(200.0);

    //Datos prueba Puesto
    Puesto puesto1= new Puesto("Santa Lucia", "Ruta 11 km 81");
    Puesto puesto2= new Puesto("Pando", "Ruta 8 km 45");
    Puesto puesto3= new Puesto("Canelones", "Ruta 5 km 23");
    Puesto puesto4= new Puesto("Colonia", "Ruta 1 km 120");

    //Datos prueba Bonificacion
    Bonificacion b1= new Frecuente() ;
    Bonificacion b2= new Exonerado();
    Bonificacion b3= new Trabajador();
    
   //Datos prueba Estado 
    Estado e1= new Habilitado(p1);
    // Estado e2= new Inhabilitado();
    // Estado e3= new Suspendido();
    // Estado e4= new Penalizado();

    // ...

    Calendar cal = Calendar.getInstance();
    cal.set(2025, Calendar.JUNE, 15, 10, 30, 0);
    Date fechaHora = cal.getTime();
    //Datos prueba Transito
    Transito tr1= new Transito( v1, fechaHora, puesto1, 90.0);

    //Datos prueba Notificacion
    Notificacion n1= new Notificacion("Transito registrado con exito");
    //Agregar datos de prueba a la fachada
        fachada.agregarUsuario(a1);
        fachada.agregarUsuario(a2);
        fachada.agregarUsuario(p1);

        fachada.agregarCategoria(c1);
        fachada.agregarCategoria(c2);
        fachada.agregarCategoria(c3);
        fachada.agregarCategoria(c4);

        fachada.agregarVehiculo(v1);
        fachada.agregarVehiculo(v2);
        fachada.agregarVehiculo(v3);
        fachada.agregarVehiculo(v4);

        fachada.agregarTarifa(t1);
        fachada.agregarTarifa(t2);
        fachada.agregarTarifa(t3);

        fachada.agregarPuesto(puesto1);
        fachada.agregarPuesto(puesto2);
        fachada.agregarPuesto(puesto3);
        fachada.agregarPuesto(puesto4);

        

        fachada.agregarEstado(e1);

        fachada.agregarTransito(tr1);

        fachada.agregarNotificacion(n1);


      // Datos de prueba para el sistema de peaje


       
    }
}
