package gui.testes.camel.activemq;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.impl.DefaultCamelContext;

import gui.testes.camel.models.Endereco;

public class EnviandoObjeto {
	
	public static void main(String[] args) throws Exception {
		
		CamelContext c = new DefaultCamelContext();
		ActiveMQComponent activeMq = ActiveMQComponent.activeMQComponent("tcp://localhost:61616");
		activeMq.setTrustAllPackages(true);
		c.addComponent("activemq", activeMq);
		
		c.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				
				from("direct:start")
					.log("Adicionando objeto: ${body}")
				.to("activemq:queue:objetos?jmsMessageType=Object");
				
			}
			
		});
		
		c.start();
		
		ProducerTemplate pt = c.createProducerTemplate();
		pt.sendBody("direct:start", new Endereco("00000-000", "Rua teste", "número 100, ap. 48"));
		//pt.sendBody("direct:start", LocalDateTime.now().toString());
		
	}
	
}
