package gui.testes.camel.models;

import java.io.Serializable;

public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String cep;
	private String rua;
	private String complemento;
	
	public Endereco() {
	}
	
	public Endereco(String cep, String rua, String complemento) {
		this.cep = cep;
		this.rua = rua;
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
