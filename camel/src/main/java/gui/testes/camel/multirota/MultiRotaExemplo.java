package gui.testes.camel.multirota;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class MultiRotaExemplo {
	
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("file:pagamentos?noop=true")
				.multicast() // espalha os arquivos para ambas as rotas. Sem o multicast(), iria ser executado na ordem definida!
					.to("direct:boleto")
					.to("direct:cartao");
				
				from("direct:cartao")
					.filter() // filtrando
						.xpath("/pagamento/tipoPagamento[text() = 'CARTAO']") // condição para filtrar
						.log("Enviando pagamento por CARTÃO")
				.to("mock:endpoint-para-cartao"); // simula um endpoint
				
				from("direct:boleto")
					.filter() // filtrando
						.xpath("/pagamento/tipoPagamento[text() = 'BOLETO']") // condição para filtrar
					.log("Enviando pagamento por BOLETO")
				.to("mock:endpoint-para-boleto"); // simula um endpoint
			}
			
		});
		
		context.start();
		Thread.sleep(20 * 1000);
		context.stop();
	}

}
