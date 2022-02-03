package gui.springcamel.usuariosapi.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import gui.springcamel.usuariosapi.entities.CpfValido;

@Component
public class CpfRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:cpf-route")
			.routeId("cpf-route")
			.log("Enviando cpf ${body} para microsseriviço de validar cpf")
			.setProperty("cpf", simple("${body}"))
			.setHeader(Exchange.HTTP_QUERY, simple("cpf=${exchangeProperty.cpf}"))
			.to("http://localhost:8081/camel/validarCpf")
			.unmarshal()
				.json(JsonLibrary.Jackson, CpfValido.class);
	}

}
