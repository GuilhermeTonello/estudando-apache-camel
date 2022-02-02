package gui.testes.camel.http;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class EnviandoArquivoParaEndpoint {

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
		
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				errorHandler(
						deadLetterChannel("activemq:queue:comidas.DLQ")
						.maximumRedeliveries(3)
						.redeliveryDelay(2 * 1000)
						.onRedelivery(exchange -> {
							System.out.println("Erro ao enviar arquivo. Tentando novamente!");
						})
						.onExceptionOccurred(exchange -> {
							String erro = (String) exchange.getIn().getBody();
							System.out.println("Falha ao enviar arquivo!");
							System.out.println(erro);
						})
					);
				
				from("file:comidas?noop=true")
				.to("json-validator:comida-schema.json")
					.setHeader(Exchange.HTTP_METHOD, HttpMethods.POST)
					.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.to("http://localhost:8080/comidas")
				.log("Salvando ${file:name}");
			}
			
		});
		
		context.start();
		Thread.sleep(20 * 1000);
		context.stop();
	}
	
}
