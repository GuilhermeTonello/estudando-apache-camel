package gui.springcamel.usuariosapi.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import gui.springcamel.usuariosapi.entities.CpfValido;

public class ValidarCpfValidator implements ConstraintValidator<ValidarCpf, String> {

	@Autowired
	private ProducerTemplate producerTemplate;
	
	@Autowired
	private CamelContext camelContext;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        Exchange sendExchange = ExchangeBuilder.anExchange(camelContext).withBody(value).build();
        Exchange outExchange = producerTemplate.send("direct:cpf-route", sendExchange);
        CpfValido result = outExchange.getMessage().getBody(CpfValido.class);
        
		return result.getValido();
	}

}
