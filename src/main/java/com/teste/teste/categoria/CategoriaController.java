package com.teste.teste.categoria;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.teste.categoria.dto.CategoriaDTO;
import com.teste.teste.categoria.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	private final CategoriaService service;

	public CategoriaController(final CategoriaService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<CategoriaDTO> carregar(@RequestBody CategoriaDTO categoria) {
		return ResponseEntity.ok(service.salvar(categoria));
	}

	@GetMapping
	public ResponseEntity<String> carregar() {
		return ResponseEntity.ok("deu certo");
	}

}
