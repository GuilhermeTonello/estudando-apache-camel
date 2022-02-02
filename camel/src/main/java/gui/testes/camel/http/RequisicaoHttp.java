package gui.testes.camel.http;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class RequisicaoHttp {

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {

				from("timer://simpleTimer?repeatCount=1") // cria um timer que executa apenas uma vez
				.log("Fazendo requisição para lista de comidas")
				.setHeader(Exchange.HTTP_METHOD, HttpMethods.GET)
				.to("http://localhost:8080/comidas") // faz a requisição
					.process(exchange -> {
						String response = exchange.getIn().getBody(String.class); // recupera a resposta da requisição
						exchange.getMessage().setBody(response); // coloca como corpo da mensagem a resposta da requisição
					})
					.setHeader(Exchange.FILE_NAME, constant("comidas.json")) // coloca o nome do arquivo como comidas.json
					.log("Salvando comidas.json na pasta http")
				.to("file:http"); // salva o arquivo nessa pasta
			}
			
		});
		
		context.start();
		Thread.sleep(20 * 1000);
		context.stop();
	}
	
}
