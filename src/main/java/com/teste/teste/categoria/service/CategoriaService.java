package com.teste.teste.categoria.service;

import org.springframework.stereotype.Component;

import com.teste.teste.categoria.dto.CategoriaDTO;
import com.teste.teste.categoria.entity.CategoriaEntity;
import com.teste.teste.categoria.repository.CategoriaRepository;

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

}
