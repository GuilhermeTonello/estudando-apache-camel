package gui.testes.camel.activemq;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.impl.DefaultCamelContext;

import gui.testes.camel.models.Endereco;

public class ConsumindoObjeto {
	
	public static void main(String[] args) throws Exception {
		
		CamelContext c = new DefaultCamelContext();
		ActiveMQComponent activeMq = ActiveMQComponent.activeMQComponent("tcp://localhost:61616");
		activeMq.setTrustAllPackages(true);
		c.addComponent("activemq", activeMq);
		
		c.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				
				from("activemq:queue:objetos?jmsMessageType=Object")
					.log("Recuperando objeto: ${body}")
					.process(e -> {
						/*
						LocalDateTime data = LocalDateTime.parse((CharSequence) e.getMessage().getBody());
						System.out.println(data);
						System.out.println(data.plusDays(1));
						*/
						Endereco en = (Endereco) e.getMessage().getBody();
						System.out.println(en.getCep());
					});
				
			}
			
		});
		
		c.start();
		
	}
	
}
