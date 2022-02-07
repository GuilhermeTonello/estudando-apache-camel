package gui.springcamel.cpfvalidatorapi.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import gui.springcamel.cpfvalidatorapi.services.ValidarCpfService;

@Component
public class ValidarCpfRoute extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		
		restConfiguration()
			.component("servlet")
			.host("localhost")
			.port(8081)
			.bindingMode(RestBindingMode.json);
		
		rest("/validarCpf")
			.get()
				.produces(MediaType.APPLICATION_JSON_VALUE)
				.param()
					.name("cpf")
					.required(true)
					.type(RestParamType.query)
				.endParam()
				.route()
					.routeId("route-validar-cpf")
				.log("Validando cpf ${header.cpf}")
				.bean(ValidarCpfService.class, "cpfValido(${header.cpf})")
		.endRest();
		
	}
	
}
