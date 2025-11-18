package da.obligatorio.peajes.modelo;

public abstract class Estado {
    
    private String nombre;
    private Propietario propietario;

    public Estado( String nombre,Propietario propietario ) {
        
        this.nombre = nombre;
        this.propietario=propietario;
    }

    public String getNombre() {
        return nombre;
    }

    public Propietario getPropietario(){
        return propietario;
    }

    public abstract void habilitar() throws PeajeException;

    public abstract void desHabilitar() throws PeajeException;

    public abstract void penalizar() throws PeajeException;

    public abstract void suspender() throws PeajeException;

    /**
     * Por defecto el estado devuelve false salvo si está habilitado (subclase
     * hija); => sobrescribe este método puedeLoguearse() y lo pasa a true
     */
    public boolean puedeLoguearse() {
        return false;
    }

}
