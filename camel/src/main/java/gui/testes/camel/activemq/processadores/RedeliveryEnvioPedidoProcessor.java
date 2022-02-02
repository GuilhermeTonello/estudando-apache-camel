package gui.testes.camel.activemq.processadores;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedeliveryEnvioPedidoProcessor implements Processor {

	private final Logger logger = LoggerFactory.getLogger(RedeliveryEnvioPedidoProcessor.class); // definindo o logger para fazer log nessa classe
	
	@Override
	public void process(Exchange exchange) throws Exception {
		int tentativaAtual = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER); // tentativa atual
		int tentativasTotais = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_MAX_COUNTER); // n�mero máximo de tentativas de envio
		String nomeDoArquivo = (String) exchange.getIn().getHeader(Exchange.FILE_NAME); // nome do arquivo que está tentando enviar
		
		logger.info("Falha no envio do pedido " + nomeDoArquivo + ".");
		logger.info("Tentando enviar novamente.");
		logger.info("Tentativa " + tentativaAtual + " de " + tentativasTotais + ".");
	}

}
