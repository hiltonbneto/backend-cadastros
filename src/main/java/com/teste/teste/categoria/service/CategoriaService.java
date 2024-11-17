package com.teste.teste.categoria.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.teste.teste.categoria.dto.CategoriaDTO;
import com.teste.teste.categoria.entity.CategoriaEntity;
import com.teste.teste.categoria.repository.CategoriaRepository;
import com.teste.teste.exception.ValidacaoException;
import com.teste.teste.produto.entity.ProdutoEntity;
import com.teste.teste.produto.repository.ProdutoRepository;

@Component
public class CategoriaService {

	private final CategoriaRepository repository;
	
	private final ProdutoRepository produtoRepository;

	public CategoriaService(final CategoriaRepository repository, final ProdutoRepository produtoRepository) {
		this.repository = repository;
		this.produtoRepository = produtoRepository;
	}

	public CategoriaDTO salvar(CategoriaDTO categoria) throws ValidacaoException {
		if (Objects.isNull(categoria)) {
			throw new ValidacaoException("Objeto inválido");
		}
		CategoriaEntity entity = CategoriaEntity.fromCategoriaDTO(categoria);
		repository.save(entity);
		return entity.toCategoriaDTO();
	}

	public List<CategoriaDTO> carregar() {
		return repository.findAll().stream().map(c -> c.toCategoriaDTO()).toList();
	}

	public void remover(Long id) throws ValidacaoException {
		Optional<CategoriaEntity> opEntity = repository.findById(id);
		if (opEntity.isEmpty()) {
			throw new ValidacaoException("Categoria ID %d não encontrado".formatted(id));
		}
		List<ProdutoEntity> produtosCategoria = produtoRepository.findByCategoria(opEntity.get());
		if (!produtosCategoria.isEmpty()) {
			throw new ValidacaoException("Não é possível remover a categora ID %d, pois esta sendo utilizada nos produtos".formatted(id));
		}
		repository.delete(opEntity.get());
	}

}
