package com.teste.teste.categoria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.teste.teste.categoria.dto.CategoriaDTO;
import com.teste.teste.categoria.entity.CategoriaEntity;
import com.teste.teste.categoria.repository.CategoriaRepository;
import com.teste.teste.exception.ValidacaoException;
import com.teste.teste.produto.entity.ProdutoEntity;
import com.teste.teste.produto.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

	@InjectMocks
	CategoriaService service;

	@Mock
	private CategoriaRepository repository;

	@Mock
	private ProdutoRepository produtoRepository;

	@Captor
	private ArgumentCaptor<CategoriaEntity> captor;

	@Test
	void naoDeveCadastrarCategoriaInvalida() {
		Assertions.assertThrows(ValidacaoException.class, () -> service.salvar(null));
	}

	@Test
	void deveCadastrarCategoria() {
		CategoriaDTO dto = new CategoriaDTO(null, "teste");
		try {
			service.salvar(dto);
			BDDMockito.then(repository).should().save(captor.capture());
			CategoriaEntity entity = captor.getValue();
			Assertions.assertEquals(dto.descricao(), entity.getDescricao());
		} catch (ValidacaoException e) {
			Assertions.fail();
		}
	}

	@Test
	void naoDeveRemoverCategoriaInvalida() {
		BDDMockito.given(repository.findById(0l)).willReturn(Optional.empty());
		Assertions.assertThrows(ValidacaoException.class, () -> service.remover(0l));
	}

	@Test
	void naoDeveRemoverCategoriaUtilizada() {
		CategoriaEntity entity = new CategoriaEntity();
		entity.setDescricao("teste categoria");
		entity.setId(0l);
		BDDMockito.given(repository.findById(0l)).willReturn(Optional.of(entity));
		BDDMockito.given(produtoRepository.findByCategoria(entity)).willReturn(List.of(new ProdutoEntity()));
		Assertions.assertThrows(ValidacaoException.class, () -> service.remover(0l));
	}

	@Test
	void deveRemoverCategoria() {
		CategoriaEntity entity = new CategoriaEntity();
		entity.setDescricao("teste categoria");
		entity.setId(0l);
		BDDMockito.given(repository.findById(0l)).willReturn(Optional.of(entity));
		BDDMockito.given(produtoRepository.findByCategoria(entity)).willReturn(new ArrayList<>());
		try {
			service.remover(0l);
		} catch (ValidacaoException e) {
			Assertions.fail();
		}
		BDDMockito.then(repository).should().delete(captor.capture());
	}
}
