package com.teste.teste.produto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.teste.produto.dto.ProdutoDTO;
import com.teste.teste.produto.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	private final ProdutoService service;

	public ProdutoController(final ProdutoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ProdutoDTO> carregar(@RequestBody ProdutoDTO categoria) {
		return ResponseEntity.ok(service.salvar(categoria));
	}

}
