package da.obligatorio.peajes.dto;

import da.obligatorio.peajes.modelo.Tarifa;

public class TarifaDTO {
    private int monto;
    private String categoria;

    public TarifaDTO(Tarifa tarifa){
        this.monto=(int) tarifa.getMonto();
        this.categoria=tarifa.getCategoria().getNombre();
    }

    public String getCategoria() {
        return categoria;
    }

    public int getMontoBase() {
        return monto;
    }
}
