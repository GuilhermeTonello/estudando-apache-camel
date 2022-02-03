package gui.testes.camel.http;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class QueryParamsNaRequisicao {
	
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {

				from("timer://simpleTimer?repeatCount=1")
					.log("Fazendo requisição")
					.setProperty("menssagem", constant("Bom dia")) // constrói uma propriedade com o nome minhaMessagem contento o valor Bom dia
					.setHeader(Exchange.HTTP_METHOD, HttpMethods.GET)
					// em versões antigas do camel, usa-se ${property.menssagem}
					.setHeader(Exchange.HTTP_QUERY, simple("message=${exchangeProperty.menssagem}")) // adiciona query params na url (localhost:8080?mensagem=Bom dia)
				.to("http://localhost:8080");
				
			}
			
		});
		
		context.start();
		Thread.sleep(20 * 1000);
		context.stop();
	}

}
