package com.teste.teste.categoria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.teste.teste.categoria.dto.CategoriaDTO;
import com.teste.teste.categoria.entity.CategoriaEntity;
import com.teste.teste.categoria.repository.CategoriaRepository;
import com.teste.teste.exception.ValidacaoException;

@Component
public class CategoriaService {

	private final CategoriaRepository repository;

	public CategoriaService(final CategoriaRepository repository) {
		this.repository = repository;
	}

	public CategoriaDTO salvar(CategoriaDTO categoria) {
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
		repository.delete(opEntity.get());
	}

}
