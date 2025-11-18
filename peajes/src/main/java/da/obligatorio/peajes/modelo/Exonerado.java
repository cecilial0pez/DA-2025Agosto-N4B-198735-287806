package da.obligatorio.peajes.modelo;

import java.util.Date;

public class Exonerado extends Bonificacion {

    public Exonerado() {
        super("Exonerado",1.0);
    }

    
    @Override
    public double calcularBonificacion(Propietario propietario, Vehiculo vehiculo, Puesto puesto, Date fecha)throws PeajeException   {
       return this.getPorcentajeDescuento();
    }

}