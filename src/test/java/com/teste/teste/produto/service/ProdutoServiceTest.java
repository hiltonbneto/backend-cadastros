package com.teste.teste.produto.service;

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
import com.teste.teste.produto.dto.ProdutoDTO;
import com.teste.teste.produto.entity.ProdutoEntity;
import com.teste.teste.produto.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

	@InjectMocks
	ProdutoService service;

	@Mock
	private ProdutoRepository repository;

	@Mock
	private CategoriaRepository categoriaRepository;

	@Captor
	private ArgumentCaptor<ProdutoEntity> captor;

	@Test
	void naoDeveSalvarProdutoInvalido() {
		Assertions.assertThrows(ValidacaoException.class, () -> service.salvar(null));
	}

	@Test
	void naoDeveSalvarProdutoSemCategoria() {
		Assertions.assertThrows(ValidacaoException.class, () -> service.salvar(new ProdutoDTO(null, "teste", null)));
	}

	@Test
	void naoDeveSalvarProdutoComCategoriaInvalida() {
		ProdutoDTO dto = new ProdutoDTO(null, "produto", new CategoriaDTO(0l, "categoria"));
		BDDMockito.given(categoriaRepository.findById(0l)).willReturn(Optional.empty());
		Assertions.assertThrows(ValidacaoException.class, () -> service.salvar(dto));
	}

	@Test
	void deveSalvarProduto() {
		ProdutoDTO dto = new ProdutoDTO(null, "produto", new CategoriaDTO(0l, "categoria"));
		BDDMockito.given(categoriaRepository.findById(0l)).willReturn(Optional.of(new CategoriaEntity()));
		try {
			service.salvar(dto);
		} catch (ValidacaoException e) {
			Assertions.fail();
		}
		BDDMockito.then(repository).should().save(captor.capture());
		ProdutoEntity entity = captor.getValue();
		Assertions.assertEquals(dto.descricao(), entity.getDescricao());
	}

	@Test
	void naoDeveRemoverProdutoInvalido() {
		BDDMockito.given(repository.findById(0l)).willReturn(Optional.empty());
		Assertions.assertThrows(ValidacaoException.class, () -> service.remover(0l));
	}

	@Test
	void deveRemoverProduto() {
		ProdutoEntity entity = new ProdutoEntity();
		entity.setCategoria(new CategoriaEntity());
		entity.setDescricao("Produto");
		entity.setId(0l);
		BDDMockito.given(repository.findById(0l)).willReturn(Optional.of(entity));
		try {
			service.remover(0l);
		} catch (ValidacaoException e) {
			Assertions.fail();
		}
		BDDMockito.then(repository).should().delete(captor.capture());
		ProdutoEntity entityRemover = captor.getValue();
		Assertions.assertEquals(entity.getDescricao(), entityRemover.getDescricao());
	}

}
