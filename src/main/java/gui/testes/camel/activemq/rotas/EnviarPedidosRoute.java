package gui.testes.camel.activemq.rotas;

import org.apache.camel.builder.RouteBuilder;

import gui.testes.camel.activemq.processadores.FalhaNoEnvioPedidoProcessor;
import gui.testes.camel.activemq.processadores.RedeliveryEnvioPedidoProcessor;

public class EnviarPedidosRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		errorHandler(
				deadLetterChannel("activemq:queue:pedidos.DLQ") // se der erro, envia para a queue pedidos.DLQ
				.maximumRedeliveries(2) // se der erro, ele tenta enviar um total de 2 vezes
				.redeliveryDelay(2 * 1000) // tenta enviar novamente depois de 2 segundos
				.onRedelivery(new RedeliveryEnvioPedidoProcessor()) // quando tentar enviar novamente, ele executa esse processador
				.onExceptionOccurred(new FalhaNoEnvioPedidoProcessor()) // quando falhar, ele executa esse processador
			);
		
		from("file:pedidos?noop=true") // pega os arquivos da pasta pedidos
			.routeId("rota-file:pedidos-para-activemq:pedidos")
			.log("Rota: ${routeId}")
			.to("json-validator:pedido-schema.json") // envia o pedido.json para validar de acordo com o pedido-schema.json
			.log("Enviando arquivo ${file:name} para fila pedidos")
		.to("activemq:queue:pedidos"); // envia os arquivos para a queue pedidos do activemq
	}

}
