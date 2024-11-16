package com.teste.teste.produto.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.teste.teste.categoria.repository.CategoriaRepository;
import com.teste.teste.exception.ValidacaoException;
import com.teste.teste.produto.dto.ProdutoDTO;
import com.teste.teste.produto.entity.ProdutoEntity;
import com.teste.teste.produto.repository.ProdutoRepository;

@Component
public class ProdutoService {

	private final ProdutoRepository repository;

	private final CategoriaRepository categoriaRepository;

	public ProdutoService(final ProdutoRepository repository, final CategoriaRepository categoriaRepository) {
		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
	}

	public ProdutoDTO salvar(ProdutoDTO produto) throws ValidacaoException {
		if (Objects.isNull(produto) || Objects.isNull(produto.categoria())) {
			throw new ValidacaoException("Objeto inválido");
		}
		categoriaRepository.findById(produto.categoria().id()).orElseThrow(
				() -> new ValidacaoException("Produto ID %d não encontrado".formatted(produto.categoria().id())));
		ProdutoEntity entity = ProdutoEntity.fromProdutoDTO(produto);
		repository.save(entity);
		return entity.toProdutoDTO();
	}

	public List<ProdutoDTO> carregar() {
		return repository.findAll().stream().map(c -> c.toProdutoDTO()).toList();
	}

	public void remover(Long id) throws ValidacaoException {
		Optional<ProdutoEntity> opEntity = repository.findById(id);
		if (opEntity.isEmpty()) {
			throw new ValidacaoException("Produto ID %d não encontrado".formatted(id));
		}
		repository.delete(opEntity.get());
	}

}
