package da.obligatorio.peajes.dto;

import java.text.SimpleDateFormat;
import da.obligatorio.peajes.modelo.Propietario;

public class PropietarioDTO {
    private String nombre;
    private String estado;
    private int saldo;

    public String getEstado() {
        return estado;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public PropietarioDTO(Propietario propietario) {
        this.nombre = propietario.getNombre();
        this.estado = propietario.getEstado().getNombre();
        this.saldo = propietario.getSaldo().intValue();
    }

    
}
