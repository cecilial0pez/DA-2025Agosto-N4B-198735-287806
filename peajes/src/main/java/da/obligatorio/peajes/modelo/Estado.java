package da.obligatorio.peajes.modelo;

public abstract class Estado {
    private Propietario propietario;
    private String nombre ;
    

    public Estado(Propietario propietario,String nombre) {
        this.propietario = propietario;
        this.nombre = nombre;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract void habilitar() throws PeajeException;
    public abstract void desHabilitar() throws PeajeException;
    public abstract void penalizar() throws PeajeException;
    public abstract void suspender() throws PeajeException;

}
