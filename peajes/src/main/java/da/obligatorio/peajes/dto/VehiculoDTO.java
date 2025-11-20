package da.obligatorio.peajes.dto;
import da.obligatorio.peajes.modelo.Vehiculo;


public class VehiculoDTO {
    private String matricula;
    private String modelo;
    private String color;
    private int cantidadTransitos;
    private double montoTotalGastado;
    // Información: Número de matrícula, modelo, color, cantidad
// de tránsitos realizados y monto total gastado en sus tránsitos.

   public VehiculoDTO(Vehiculo vehiculo) {
        this.matricula = vehiculo.getMatricula();
        this.modelo = vehiculo.getModelo();
        this.color = vehiculo.getColor();
        this.cantidadTransitos = vehiculo.getCantidadTransitos();
        this.montoTotalGastado = vehiculo.getMontoTotalGastado();
    }

    public int getCantidadTransitos() {
        return cantidadTransitos;
    }

    public double getMontoTotalGastado() {
        return montoTotalGastado;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

}
