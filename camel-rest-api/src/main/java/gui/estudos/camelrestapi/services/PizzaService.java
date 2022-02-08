package gui.estudos.camelrestapi.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gui.estudos.camelrestapi.entities.Ingrediente;
import gui.estudos.camelrestapi.entities.Pizza;
import gui.estudos.camelrestapi.entities.Tamanho;

@Service
public class PizzaService {
	
	private static List<Pizza> pizzas;
	
	public PizzaService() {
		Ingrediente molhoDeTomate = new Ingrediente("Molho de Tomate");
		Ingrediente queijo = new Ingrediente("Queijo");
		Ingrediente calabresa = new Ingrediente("Calabresa");
		Ingrediente cebola = new Ingrediente("Cebola");
		Ingrediente atum = new Ingrediente("Atum");
		
		pizzas = Arrays.asList(
				new Pizza(UUID.randomUUID(), "Mussarela", List.of(queijo, molhoDeTomate), BigDecimal.valueOf(28.99), Tamanho.MEDIA),
				new Pizza(UUID.randomUUID(), "Calabresa", List.of(calabresa, molhoDeTomate), BigDecimal.valueOf(28.99), Tamanho.MEDIA),
				new Pizza(UUID.randomUUID(), "Atum", List.of(atum, molhoDeTomate, cebola), BigDecimal.valueOf(28.99), Tamanho.MEDIA));
	}
	
	public Pizza addPizza(Pizza pizza) {
		pizza.setId(UUID.randomUUID());
		pizzas.add(pizza);
		return pizza;
	}
	
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	
	public Pizza getPizzaById(UUID id) {
		return pizzas.stream()
				.filter(pizza -> pizza.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Pizza não encontrada!"));
	}
	
}
