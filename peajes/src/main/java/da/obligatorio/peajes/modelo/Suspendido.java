package da.obligatorio.peajes.modelo;

public class Suspendido extends Estado {
    public Suspendido(Propietario propietario) {
        super("Suspendido", propietario);
    }

     
     @Override
    public void habilitar() throws PeajeException {
        getPropietario().setEstado(new Habilitado(getPropietario()));
    }

    @Override
    public void desHabilitar() throws PeajeException {
        throw new PeajeException("No se puede deshabilitar un propietario suspendido.");
    }

    @Override
    public void penalizar() throws PeajeException {
        throw new PeajeException("No se puede penalizar un propietario suspendido.");
    }

    @Override
    public void suspender() throws PeajeException {
        throw new PeajeException("El propietario ya está suspendido.");
    }

}
