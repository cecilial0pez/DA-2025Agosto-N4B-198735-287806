package da.obligatorio.peajes.modelo;

public class Deshabilitado extends Estado {

    public Deshabilitado(Propietario propietario) {
        super( "Deshabilitado", propietario);
    }

    @Override
    public void habilitar() throws PeajeException {
        getPropietario().cambiarEstado(new Habilitado(getPropietario()));
    }

    @Override
    public void desHabilitar() throws PeajeException {
        throw new PeajeException("Ya está deshabilitado");
    }

    @Override
    public void penalizar() throws PeajeException {
        throw new PeajeException("No se puede penalizar, ya está deshabilitado");
    }

    @Override
    public void suspender() throws PeajeException {
        throw new PeajeException("No se puede suspender, ya está deshabilitado");
    }
}
