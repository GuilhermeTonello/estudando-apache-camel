package gui.testes.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.impl.DefaultCamelContext;

import gui.testes.camel.rotas.EnviarPedidosRoute;

public class EnviarPedidos {
	
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
		
		context.addRoutes(new EnviarPedidosRoute());
		
		context.start();
		Thread.sleep(20 * 1000);
		context.stop();
	}
	
}
