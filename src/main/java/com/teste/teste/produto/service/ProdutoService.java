package com.teste.teste.produto.service;

import org.springframework.stereotype.Component;

import com.teste.teste.produto.dto.ProdutoDTO;
import com.teste.teste.produto.entity.ProdutoEntity;
import com.teste.teste.produto.repository.ProdutoRepository;

@Component
public class ProdutoService {
	
	private final ProdutoRepository repository;

	public ProdutoService(final ProdutoRepository repository) {
		this.repository = repository;
	}

	public ProdutoDTO salvar(ProdutoDTO produto) {
		ProdutoEntity entity = ProdutoEntity.fromProdutoDTO(produto);
		repository.save(entity);
		return entity.toProdutoDTO();
	}

}
