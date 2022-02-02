package gui.testes.camel.activemq.processadores;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FalhaNoEnvioPedidoProcessor implements Processor {

	private final Logger logger = LoggerFactory.getLogger(FalhaNoEnvioPedidoProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		String nomeDoArquivo = (String) exchange.getIn().getHeader(Exchange.FILE_NAME);
		
        Throwable ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
        exchange.getIn().setBody(ex.getMessage());
        String erro = (String) exchange.getIn().getBody();
		
		logger.info("Erro no envio do arquivo " + nomeDoArquivo);
		logger.info("Erro: " + erro);
	}

}
