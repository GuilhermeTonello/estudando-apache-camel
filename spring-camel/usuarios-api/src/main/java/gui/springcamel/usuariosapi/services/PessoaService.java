package gui.springcamel.usuariosapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gui.springcamel.usuariosapi.entities.Pessoa;

@Service
public class PessoaService {
	
private final List<Pessoa> pessoas;
	
	public PessoaService() {
		pessoas = new ArrayList<>();
		
		pessoas.add(new Pessoa(UUID.randomUUID(), "Jubilei", "09989607052"));
		pessoas.add(new Pessoa(UUID.randomUUID(), "Zézão", "94393207025"));
		pessoas.add(new Pessoa(UUID.randomUUID(), "Guilherme", "39332499020"));
	}
	
	public List<Pessoa> findAll() {
		return pessoas;
	}
	
	public Pessoa findById(UUID id) {
		return pessoas.stream()
				.filter(pessoa -> pessoa.getId().equals(id) )
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Pessoa não encontrada!"));
	}
	
	public void save(Pessoa pessoa) {
		pessoa.setId(UUID.randomUUID());
		pessoas.add(pessoa);
	}

}
