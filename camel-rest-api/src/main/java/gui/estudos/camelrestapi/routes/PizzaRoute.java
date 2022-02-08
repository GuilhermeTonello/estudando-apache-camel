package gui.estudos.camelrestapi.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import gui.estudos.camelrestapi.services.PizzaService;

@Component
public class PizzaRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		restConfiguration()
			.bindingMode(RestBindingMode.json);
		
		rest("/pizzas")
			.get()
				.route()
				.routeId("route-pizza-list")
			.bean(PizzaService.class, "getPizzas")
			.end()
		.endRest();
		
	}

}
