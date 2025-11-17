package da.obligatorio.peajes.dto;

import da.obligatorio.peajes.modelo.Propietario;
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
    private String nombrePropietario;
    private String estadoPropietario;
    private String categoria;
    private double saldoPropietario;
 

    public TransitoDTO(Transito transito){
        this.puesto= transito.getPuesto().getNombre();
        this.matricula= transito.getVehiculo().getMatricula();
        this.Nombretarifa= transito.getTarifa().getCategoria().getNombre();
        this.montoTarifa=transito.getTarifa().getMonto();
        this.nombreBonificacion= transito.getNombreBonificacion();
        // this.montoBonificacion= transito.getMontoBonificacion();
        this.montoPagado= transito.getTotalPagado();
        this.fechayHora= transito.getFechaHora().toString();
        

    }

    public TransitoDTO(Transito transito, Propietario propietario){
        this.nombrePropietario= propietario.getNombre();
        this.estadoPropietario= propietario.getEstado().getNombre();
        this.categoria= transito.getVehiculo().getCategoria().getNombre();
        this.nombreBonificacion= transito.getNombreBonificacion();
        this.montoPagado= transito.getTotalPagado();
        this.saldoPropietario= propietario.getSaldo();
    }


    public String getNombrePuesto() { return puesto; }
    public String getMatricula() { return matricula; }
    public String getNombreTarifa() { return Nombretarifa; }
    public double getMontoTarifa() { return montoTarifa; }
    public String getNombreBonificacion() { return nombreBonificacion; }
    public double getMontoBonificacion() { return montoBonificacion; }
    public double getTotalPagado() { return montoPagado; }
    public String getFechaHora() { return fechayHora; }
    public String getNombrePropietario() { return nombrePropietario; }
    public String getEstadoPropietario() { return estadoPropietario; }
    public String getCategoria() { return categoria; }
    public double getSaldoPropietario() { return saldoPropietario; }

}
