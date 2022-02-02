package gui.testes.api.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gui.testes.api.entities.Categoria;
import gui.testes.api.entities.Comida;

@RestController
@RequestMapping("comidas")
public class ComidaController {
	
	private final List<Comida> comidas;
	
	public ComidaController() {
		comidas = new ArrayList<>();
		
		comidas.add(new Comida(UUID.randomUUID(), "Panquecas", Arrays.asList("Massa de panqueca", "Mel", "Manteiga"), Categoria.CAFE_DA_MANHA));
		comidas.add(new Comida(UUID.randomUUID(), "Bife", Arrays.asList("Bife de vaca", "Manteiga"), Categoria.CARNE_VACA));
		comidas.add(new Comida(UUID.randomUUID(), "Sushi", Arrays.asList("Salmão", "Arroz", "Alga"), Categoria.CARNE_PEIXE));
	}
	
	@GetMapping
	public List<Comida> findAll() {
		return comidas;
	}
	
	@GetMapping("{id}")
	public Comida findById(@PathVariable UUID id) {
		return comidas.stream()
				.filter(comida -> comida.getId().equals(id) )
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Comida não encontrada!"));
	}
	
}
