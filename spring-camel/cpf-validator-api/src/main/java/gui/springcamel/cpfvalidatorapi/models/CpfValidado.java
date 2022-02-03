package gui.springcamel.cpfvalidatorapi.models;

public class CpfValidado {

	private Boolean valido;
	
	public CpfValidado() {
	}
	
	public CpfValidado(Boolean valido) {
		this.valido = valido;
	}
	
	public Boolean getValido() {
		return valido;
	}
	
	public void setValido(Boolean valido) {
		this.valido = valido;
	}
	
}
