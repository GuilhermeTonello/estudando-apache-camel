package gui.testes.camel.simples;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;

import gui.testes.camel.models.Pedido;

public class BasicoCamel {
	
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext(); // instanciando o contexto do camel
		
		context.addRoutes(new RouteBuilder() { // criando uma rota com o camel
			
			@Override
			public void configure() throws Exception {
				
				from("file:pedidos?noop=true") // resgatando os arquivos dentro da pasta pedidos. noop=true => faz com que os arquivos continuem também pasta pedidos
					.routeId("rota-de-pedidos-alimentos") // definindo um id para essa rota
					.filter() // iniciando uma filtragem de dados
						.jsonpath("$.itens[?(@.categoria == 'ALIMENTOS')]") // separando apenas os dados que possuem itens com categoria ALIMENTOS
					.unmarshal()
						.json(JsonLibrary.Jackson, Pedido.class) // convertendo para objeto
					.marshal()
						.jacksonxml(true) // convertendo para xml. esse 'true' é para fazer a identação das linhas
					.setHeader(Exchange.FILE_NAME, simple("${file:name.noext}.xml"))
					.log("Convertendo arquivo ${file:name} para ${file:name.noext}.xml")
				.to("file:saida"); // enviando a resposta para a pasta saida
			}
			
		});
		
		context.start(); // iniciando o camel
		Thread.sleep(20 * 1000); // dando 20 segundos para o camel pensar
		context.stop(); // parando o camel
	}
	
}
