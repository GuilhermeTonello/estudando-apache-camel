package gui.springcamel.cpfvalidatorapi.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import gui.springcamel.cpfvalidatorapi.services.ValidarCpfService;

@Component
public class ValidarCpfRoute extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		
		rest("/validarCpf")
			.get()
				.param()
					.name("cpf")
					.required(true)
					.type(RestParamType.query)
				.endParam()
				.route()
				.log("Validando cpf ${header.cpf}")
				.bean(ValidarCpfService.class, "cpfValido(${header.cpf})")
				.marshal()
					.json();
		
	}
	
}
