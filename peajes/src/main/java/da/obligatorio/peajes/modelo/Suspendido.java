package da.obligatorio.peajes.modelo;

public class Suspendido extends Estado {
    public Suspendido(Propietario propietario) {
        super("Suspendido", propietario);
    }

     
     @Override
    public void habilitar() throws PeajeException {
       getPropietario().aplicarCambioDirecto(new Habilitado(getPropietario()));
    }

    @Override
    public void desHabilitar() throws PeajeException {
        getPropietario().aplicarCambioDirecto(new Deshabilitado(getPropietario()));
    }

    @Override
    public void penalizar() throws PeajeException {
        getPropietario().aplicarCambioDirecto(new Penalizado(getPropietario()));
    }

    @Override
    public void suspender() throws PeajeException {
        throw new PeajeException("El propietario ya está suspendido.");
    }

}
