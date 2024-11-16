package com.teste.teste.produto.entity;

import com.teste.teste.categoria.entity.CategoriaEntity;
import com.teste.teste.produto.dto.ProdutoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity(name = "produto")
public class ProdutoEntity {

	@Id
	@SequenceGenerator(name = "produto_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_id_seq")
	@Column(name = "id", columnDefinition = "bigserial", updatable = false)
	private Long id;

	@Column(name = "descricao", nullable = false)
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	private CategoriaEntity categoria;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public CategoriaEntity getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaEntity categoria) {
		this.categoria = categoria;
	}

	public static ProdutoEntity fromProdutoDTO(ProdutoDTO dto) {
		ProdutoEntity entity = new ProdutoEntity();
		entity.setId(dto.id());
		entity.setDescricao(dto.descricao());
		entity.setCategoria(CategoriaEntity.fromCategoriaDTO(dto.categoria()));
		return entity;
	}

	public ProdutoDTO toProdutoDTO() {
		return new ProdutoDTO(this.id, this.descricao, this.categoria.toCategoriaDTO());
	}

}
