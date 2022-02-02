package gui.testes.camel.rotas;

import org.apache.camel.builder.RouteBuilder;

public class ReceberPedidosRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("activemq:queue:pedidos");
	}

}
