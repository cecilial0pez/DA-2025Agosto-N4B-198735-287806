package da.obligatorio.peajes.modelo;

public class Penalizado extends Estado {
    public Penalizado(Propietario propietario) {
        super( "Penalizado",propietario);
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
        throw new PeajeException("El propietario ya está penalizado.");
    }

    @Override
    public void suspender() throws PeajeException {
       getPropietario().cambiarEstado(new Suspendido(getPropietario()));
     }
    
}
