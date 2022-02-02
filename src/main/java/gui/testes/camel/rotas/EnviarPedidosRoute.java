package gui.testes.camel.rotas;

import org.apache.camel.builder.RouteBuilder;

public class EnviarPedidosRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:pedidos?noop=true")
			.log("Enviando arquivo ${file:name} para fila pedidos")
		.to("activemq:queue:pedidos");
	}

}
