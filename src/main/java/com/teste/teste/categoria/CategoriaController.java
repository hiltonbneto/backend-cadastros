package com.teste.teste.categoria;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.teste.categoria.dto.CategoriaDTO;
import com.teste.teste.categoria.service.CategoriaService;
import com.teste.teste.exception.ValidacaoException;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	private final CategoriaService service;

	public CategoriaController(final CategoriaService service) {
		this.service = service;
	}
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> carregar() {
		return ResponseEntity.ok(service.carregar());
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<CategoriaDTO> salvar(@RequestBody CategoriaDTO categoria) {
		return ResponseEntity.ok(service.salvar(categoria));
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> remover(@PathVariable Long id) throws ValidacaoException {
		service.remover(id);
		return ResponseEntity.ok(true);
	}

}
