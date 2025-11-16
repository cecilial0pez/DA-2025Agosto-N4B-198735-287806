package da.obligatorio.peajes.modelo;

public class Vehiculo{
    private String matricula;
    private Categoria categoria;
    private String modelo;
    private String color;
    private Propietario propietario;

    
    public Vehiculo(String matricula, Categoria categoria, String modelo, String color, Propietario propietario) {
        this.matricula = matricula;
        this.categoria = categoria;
        this.modelo = modelo;
        this.color = color;
        this.propietario = propietario;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
    
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    //metodosAgregados


    public boolean VerificarMatricula(String matricula){
		boolean ok = false;
		if(matricula != null && matricula.length() == 7){
			// Verificar que los primeros 3 caracteres sean letras
			String primerosTres = matricula.substring(0, 3);
			boolean sonLetras = primerosTres.chars().allMatch(Character::isLetter);
			
			// Verificar que los últimos 4 caracteres sean números
			String ultimosCuatro = matricula.substring(3, 7);
			boolean sonNumeros = ultimosCuatro.chars().allMatch(Character::isDigit);
			
			ok = sonLetras && sonNumeros;
		}
		return ok;
	}
}
