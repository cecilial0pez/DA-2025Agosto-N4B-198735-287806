package da.obligatorio.peajes.modelo;


 public class DatosPrueba {
    
    public static void cargar(){
        
        Fachada fachada = Fachada.getInstancia();
       
    //Datos prueba  usuarios  
    Administrador a1= new Administrador("Admin1", "admin123", "1234567");
    Administrador a2= new Administrador("Admin2", "admin456", "8765432");
    Propietario p1= new Propietario("prop123", "1122334", "Propietario1",5000.0, new Estado("Habilitado"));
    
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
    Puesto p1= new Puesto("Puesto1", t1);
    Puesto p2= new Puesto("Puesto2", t2);

    //Datos prueba Transito (vehiculo, date, puesto, totalPagado)
  

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

        
       



      // Datos de prueba para el sistema de peaje


    //    public String matricula;
    // public Categoria categoria;
    // public String modelo;
    // public String color;


        // fachada.agregarTipoContacto("Laboral");
        // fachada.agregarTipoContacto("Particular");
        // fachada.agregarTipoContacto("Familiar");

        //  fachada.agregarTipoTelefono("Fijo");
        //  fachada.agregarTipoTelefono("Celular");
        //  fachada.agregarTipoTelefono("Internacional");
        
        // fachada.agregarUsuarioAgenda("a", "a", "Ana");
        // fachada.agregarUsuarioAgenda("b", "b", "Beatriz");
        // fachada.agregarUsuarioAgenda("c", "c", "Carlos");

        // fachada.agregarAdministrador("z", "z", "Zeta");
        // fachada.agregarAdministrador("x", "x", "Equis");

       
        
    
       
    
        
       
    }
}
