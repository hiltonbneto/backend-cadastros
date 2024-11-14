package com.teste.teste.produto.dto;

import com.teste.teste.categoria.dto.CategoriaDTO;

public record ProdutoDTO(Long id, String descricao, CategoriaDTO categoria) {
	
	

}
