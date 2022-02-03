package gui.testes.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	
	@GetMapping
	public String message(@RequestParam(defaultValue = "Hello", required = false) String message) {
		System.out.println(message);
		return message;
	}
	
}
