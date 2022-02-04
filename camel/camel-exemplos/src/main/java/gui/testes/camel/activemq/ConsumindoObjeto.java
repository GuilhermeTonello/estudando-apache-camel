package gui.testes.camel.activemq;

import java.util.Arrays;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import gui.testes.camel.models.Endereco;

public class ConsumindoObjeto {
	
	public static void main(String[] args) throws Exception {
		
		CamelContext c = new DefaultCamelContext();
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		factory.setTrustedPackages(Arrays.asList("gui.testes.camel.models"));
		
		JmsComponent jms = new JmsComponent();
		jms.setConnectionFactory(factory);
		
		c.addComponent("activemq", jms);
		
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
