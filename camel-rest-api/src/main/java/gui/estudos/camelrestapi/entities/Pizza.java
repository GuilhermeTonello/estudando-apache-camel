package gui.estudos.camelrestapi.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Pizza {

	private UUID id;
	private String sabor;
	private List<Ingrediente> ingredientes;
	private BigDecimal preco;
	private Tamanho tamanho;
	
}
