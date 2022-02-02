package gui.testes.camel.rotas;

import org.apache.camel.builder.RouteBuilder;

public class FinalizarPedidosRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("activemq:queue:pedidos")
			.log("Recebendo pedido da fila de pedidos")
			.log("${body}")
			.log("Enviando pedido para a fila de pedidos finalizados")
		.to("activemq:queue:pedidos.finalizados");
	}

}
