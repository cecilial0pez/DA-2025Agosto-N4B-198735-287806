package da.obligatorio.peajes.modelo;

public class Habilitado extends Estado {

    public Habilitado(Propietario propietario) {
        super(propietario, "Habilitado");
    }

    @Override
    public void habilitar() throws PeajeException {
        throw new PeajeException("Ya está habilitado");
    }

    @Override
    public void desHabilitar() throws PeajeException {
        getPropietario().cambiarEstado(new Deshabilitado(getPropietario()));
    }
// poner lo que corresponda
     @Override
    public void penalizar() throws PeajeException {
        throw new PeajeException("No se puede penalizar, ya está deshabilitado");
    }

    @Override
    public void suspender() throws PeajeException {
        throw new PeajeException("No se puede suspender, ya está deshabilitado");
    }
    
}
