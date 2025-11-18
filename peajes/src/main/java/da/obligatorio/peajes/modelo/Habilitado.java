package da.obligatorio.peajes.modelo;

public class Habilitado extends Estado {

    public Habilitado(Propietario propietario  ) {
        super(  "Habilitado", propietario);
    }


    @Override
    public void habilitar() throws PeajeException {
        throw new PeajeException("Ya está habilitado");
    }

    @Override
    public void desHabilitar() throws PeajeException {
       getPropietario().cambiarEstado(new Deshabilitado(getPropietario()));
    }

    @Override
    public void penalizar() throws PeajeException {
         getPropietario().cambiarEstado(new Penalizado(getPropietario()));
    }

    @Override
    public boolean puedeLoguearse() {
        return true;
    }

    @Override
     public void suspender() throws PeajeException {
        getPropietario().cambiarEstado(new Suspendido(getPropietario()));
     }

}
