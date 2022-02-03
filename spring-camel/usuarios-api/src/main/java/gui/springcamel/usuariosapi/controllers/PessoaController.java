package gui.springcamel.usuariosapi.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gui.springcamel.usuariosapi.entities.Pessoa;
import gui.springcamel.usuariosapi.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	private final PessoaService pessoaService;
	
	public PessoaController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}
	
	@GetMapping
	public List<Pessoa> findAll() {
		return pessoaService.findAll();
	}
	
	@GetMapping("{id}")
	public Pessoa findById(@PathVariable UUID id) {
		return pessoaService.findById(id);
	}
	
	@PostMapping
	public void save(@RequestBody @Valid Pessoa pessoa) {
		pessoaService.save(pessoa);
	}
	
}
