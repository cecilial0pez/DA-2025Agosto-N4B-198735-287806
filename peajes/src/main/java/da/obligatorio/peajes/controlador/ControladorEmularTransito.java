package da.obligatorio.peajes.controlador;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;
import observador.Observable;
import observador.Observador;

@RestController
@Scope("session")
public class ControladorEmularTransito implements Observador{

    @Override
    public void actualizar(Object evento, Observable origen) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

}
