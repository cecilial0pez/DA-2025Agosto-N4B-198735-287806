package da.obligatorio.peajes.modelo;

public class Deshabilitado extends Estado {

    public Deshabilitado(Propietario propietario) {
        super( "Deshabilitado", propietario);
    }

    @Override
    public void habilitar() throws PeajeException {
          getPropietario().aplicarCambioDirecto(new Habilitado(getPropietario()));
    }

    @Override
    public void desHabilitar() throws PeajeException {
        throw new PeajeException("Ya está deshabilitado");
    }

    @Override
    public void penalizar() throws PeajeException {
        getPropietario().aplicarCambioDirecto(new Penalizado(getPropietario()));
    }
    

    @Override
    public void suspender() throws PeajeException {
        getPropietario().aplicarCambioDirecto(new Suspendido(getPropietario()));
    }
    
    @Override
    public boolean puedeLoguearse() {
        return false;
    }

}
