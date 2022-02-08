package gui.estudos.camelrestapi.routes;

import java.util.UUID;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import gui.estudos.camelrestapi.entities.Pizza;
import gui.estudos.camelrestapi.services.PizzaService;

@Component
public class PizzaRoute extends RouteBuilder {

	private final PizzaService pizzaService;
	
	public PizzaRoute(PizzaService pizzaService) {
		this.pizzaService = pizzaService;
	}

	@Override
	public void configure() throws Exception {
		
		restConfiguration()
			.bindingMode(RestBindingMode.json);
		
		rest("/pizzas")
			.get()
				.produces(MediaType.APPLICATION_JSON_VALUE)
				.route()
					.routeId("route-pizza-list")
					.log("Listando pizzas")
				.bean(PizzaService.class, "getPizzas")
			.endRest()
			.get("/{id}")
				.produces(MediaType.APPLICATION_JSON_VALUE)
				.param()
					.name("id")
					.required(true)
					.type(RestParamType.path)
				.endParam()
				.route()
					.routeId("route-pizza-search-by-id")
					.log("Procurando pizza com id: ${header.id}")
				.to("bean:pizzaService?method=getPizzaById(${header.id})")
			.endRest()
			.post()
				.consumes(MediaType.APPLICATION_JSON_VALUE)
				.type(Pizza.class)
				.outType(Pizza.class)
				.route()
					.routeId("route-pizza-save-new")
					.log("Salvando nova pizza")
				.bean(PizzaService.class, "addPizza(${body})")
			.endRest()
			.delete("/{id}")
				.param()
					.name("id")
					.required(true)
					.type(RestParamType.path)
				.endParam()
				.route()
					.routeId("route-pizza-delete-by-id")
					.log("Deletando pizza com id: ${header.id}")
				.bean(PizzaService.class, "deletePizzaById(${header.id})")
			.endRest()
			.put("/{id}")
				.consumes(MediaType.APPLICATION_JSON_VALUE)
				.type(Pizza.class)
				.outType(Pizza.class)
				.param()
					.name("id")
					.required(true)
					.type(RestParamType.path)
				.endParam()
				.route()
					.routeId("route-pizza-update-by-id")
					.log("Atualizando pizza com id: ${header.id}")
				.process(ex -> pizzaService.updatePizza(ex.getIn().getHeader("id", UUID.class), ex.getIn().getBody(Pizza.class)))
			.endRest();
	}

}
