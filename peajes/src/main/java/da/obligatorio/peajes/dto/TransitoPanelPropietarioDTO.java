package da.obligatorio.peajes.dto;

import da.obligatorio.peajes.modelo.Bonificacion;
import da.obligatorio.peajes.modelo.Propietario;
import da.obligatorio.peajes.modelo.Puesto;
import da.obligatorio.peajes.modelo.Tarifa;
import da.obligatorio.peajes.modelo.Transito;

public class TransitoPanelPropietarioDTO {
    private String puesto;
    private String matricula;
    private String Nombretarifa;
    private double montoTarifa;
    private String nombreBonificacion;
    private double montoBonificacion;
    private double montoPagado;
    private String fechayHora;
    private String nombrePropietario;
    private String estadoPropietario;
    private String categoria;

  
    public TransitoPanelPropietarioDTO(Transito transito){
        this.puesto= transito.getPuesto().getNombre();
        this.matricula= transito.getVehiculo().getMatricula();
        this.Nombretarifa= transito.getTarifa().getCategoria().getNombre();
        this.montoTarifa=transito.getTarifa().getMonto();
        this.nombreBonificacion= transito.getNombreBonificacion();
         this.montoBonificacion= transito.getMontoBonificacion();
        this.montoPagado= transito.getTotalPagado();
        this.fechayHora= transito.getFechaHora().toString();
   
    }


    public String getNombrePuesto() { return puesto }
    public String getMatricula(){return matricula;}
    public String getMontoTarifa(){return montoTarifa;}
    public String getMontoBonificacion(){return montoBonificacion; }
    public String getNombreBonificacion(){return nombreBonificacion;}
    public String getNombreTarifa(){return Nombretarifa;}
    public double getTotalPagado(){return montoPagado;}
    public String getFechaHora(){return fechayHora; }


}
