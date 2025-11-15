package da.obligatorio.peajes.dto;

import da.obligatorio.peajes.modelo.Transito;

public class TransitoDTO {
    private String puesto;
    private String matricula;
    private String Nombretarifa;
    private double montoTarifa;
    private String nombreBonificacion;
    private double montoBonificacion;
    private double montoPagado;
    private String fechayHora;

    public TransitoDTO(Transito transito){
        this.puesto= transito.getPuesto().getNombre();
        this.matricula= transito.getVehiculo().getMatricula();
        this.Nombretarifa= transito.getTarifa().getCategoria().getNombre();
        this.montoTarifa=transito.getTarifa().getMonto();
        this.nombreBonificacion= 
        this.montoBonificacion=
        this.montoPagado= transito.getTotalPagado();
        this.fechayHora= transito.getFechaHora().toString();

    }


}
