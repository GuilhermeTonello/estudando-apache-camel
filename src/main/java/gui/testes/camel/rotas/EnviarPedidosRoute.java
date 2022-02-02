package gui.testes.camel.rotas;

import org.apache.camel.builder.RouteBuilder;

public class EnviarPedidosRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:pedidos?noop=true")
		.to("activemq:queue:pedidos");
	}

}
