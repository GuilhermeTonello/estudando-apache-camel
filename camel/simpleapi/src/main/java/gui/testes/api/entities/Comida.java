package gui.testes.api.entities;

import java.util.List;
import java.util.UUID;

public class Comida {
	
	private UUID id;
	private String nome;
	private List<String> ingredientes;
	private Categoria categoria;

	public Comida() {
	}
	
	public Comida(UUID id, String nome, List<String> ingredientes, Categoria categoria) {
		this.id = id;
		this.nome = nome;
		this.ingredientes = ingredientes;
		this.categoria = categoria;
	}

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<String> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}
