package da.obligatorio.peajes.modelo;


 public class DatosPrueba {
    
    public static void cargar(){
        
        Fachada fachada = Fachada.getInstancia();
        
        // fachada.agregarCriterioBusqueda(new XCombinado());
        // fachada.agregarCriterioBusqueda(new XNonbre());
        // fachada.agregarCriterioBusqueda(new XTelefono());
        
    Administrador a1= new Administrador("Admin1", "admin123", "1234567");
    Administrador a2= new Administrador("Admin2", "admin456", "8765432");
    Propietario p1= new Propietario("prop123", "1122334", "Propietario1",5000.0, new Estado("Habilitado"));
     
        fachada.agregarUsuario(a1);
        fachada.agregarUsuario(a2);
        fachada.agregarUsuario(p1);
        
        
       


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
