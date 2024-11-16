package com.teste.teste.produto;

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

import com.teste.teste.exception.ValidacaoException;
import com.teste.teste.produto.dto.ProdutoDTO;
import com.teste.teste.produto.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	private final ProdutoService service;

	public ProdutoController(final ProdutoService service) {
		this.service = service;
	}

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> carregar() {
		return ResponseEntity.ok(service.carregar());
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<ProdutoDTO> salvar(@RequestBody ProdutoDTO produto) throws ValidacaoException {
		return ResponseEntity.ok(service.salvar(produto));
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> remover(@PathVariable Long id) throws ValidacaoException {
		service.remover(id);
		return ResponseEntity.ok(true);
	}

}
