package da.obligatorio.peajes.modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SistemaPropietario {
    private ArrayList<Propietario> propietarios = new ArrayList();
    private ArrayList<Vehiculo> vehiculos=new ArrayList();

    public void agregarVehiculo(Vehiculo v) throws PeajeException {
        try{
            if(v != null && !vehiculos.contains(v) && v.VerificarMatricula(v.getMatricula())){
        vehiculos.add(v);
            }
        }catch(Exception e){
            throw new PeajeException("Vehiculo nulo o matricula invalida");
        }
    }
}
