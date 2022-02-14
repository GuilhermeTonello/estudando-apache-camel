package br.com.caelum.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidos {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
		
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				
				// o errorHandler precisa estar antes de tudo!
				errorHandler(
						deadLetterChannel("activemq:queue:pedidos.DLQ") // deadLetterChannel é uma rota para os erros
						.logExhaustedMessageHistory(true) // mostra o erro no console uma vez
						.maximumRedeliveries(2) // número máximo de tentativas para tentar enviar novamente
						.redeliveryDelay(1 * 1000) // delay para tentar novamente
						.onRedelivery(new Processor() {
							@Override
							public void process(Exchange exchange) throws Exception {
								int counter = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER);
								int max = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_MAX_COUNTER);
								System.out.println("Erro ao enviar!");
								System.out.println("Tentando enviar novamente!");
								System.out.println("Tentativa " + counter + " de " + max);
							}
						})
					); 
				
				//from("file:pedidos?delay=5s&noop=true")
				from("activemq:queue:pedidos")
				.routeId("rota-pedidos")
				.to("validator:pedido.xsd")
				.multicast() // (espalha a mensagem). Sem o multicast(), ele irá na ordem soap e depois http, podendo causar problemas
				//.parallelProcessing().setTimeout(500) // processamento em paralelo, pode ocorrer problemas que complicam a análise
				.to("direct:soap") // pode-se trocar o direct (síncrono) por seda (assíncrono e processa tudo na jvm), assim o multicast não é necessário
				.to("direct:http");
				
				from("direct:http")
					.routeId("rota-http")
					.setProperty("pedidoId", xpath("/pedido/id/text()"))
					.setProperty("clienteId", xpath("/pedido/pagamento/email-titular/text()"))
					.split()
						.xpath("/pedido/itens/item")
					.filter()
						//.xpath("/pedido/itens/item/formato[text() = 'EBOOK']") // sem o split(), se a lista de itens conter ebook, ele considera como TRUE!
						.xpath("/item/formato[text() = 'EBOOK']")
					.setProperty("ebookId", xpath("/item/livro/codigo/text()"))
					.marshal()
						.xmljson()
					.log("Convertendo ${file:name} para ${file:name.noext}.${header.CamelSplitIndex}.json")
					//.setHeader(Exchange.FILE_NAME, simple("${file:name.noext}.${header.CamelSplitIndex}.json"))
					.setHeader(Exchange.HTTP_METHOD, HttpMethods.GET)
					.setHeader(Exchange.HTTP_QUERY, simple("ebookId=${property.ebookId}&pedidoId=${property.pedidoId}&clienteId=${property.clienteId}"))
				.to("http4://localhost:8080/webservices/ebook/item");
					//.to("file:saida");
				
				from("direct:soap")
					.routeId("rota-soap")
					.to("xslt:pedido-para-soap.xslt")
					.log("Enviando nota para serviço SOAP")
					.setHeader(Exchange.CONTENT_TYPE, constant("text/xml"))
				.to("http4://localhost:8080/webservices/financeiro"); // por padrão é POST, como o SOAP sempre é POST, logo não precisa deixar explícito
				//.to("mock:soap"); // faz um mock para o lugar de envio
				
			}
		});
		
		context.start();
		Thread.sleep(20 * 1000);
		context.stop();
		
	}

}
