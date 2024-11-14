package com.teste.teste.categoria.entity;

import java.util.List;

import com.teste.teste.categoria.dto.CategoriaDTO;
import com.teste.teste.produto.entity.ProdutoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "categoria")
public class CategoriaEntity {

	@Id
	@SequenceGenerator(name = "categoria_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_id_seq")
	@Column(name = "id", columnDefinition = "bigserial", updatable = false)
	private Long id;

	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@OneToMany(mappedBy = "categoria")
  private List<ProdutoEntity> produtos;

	public static CategoriaEntity fromCategoriaDTO(CategoriaDTO dto) {
		CategoriaEntity entity = new CategoriaEntity();
		entity.setId(dto.id());
		entity.setDescricao(dto.descricao());
		return entity;
	}

	public CategoriaDTO toCategoriaDTO() {
		return new CategoriaDTO(this.id, this.descricao);
	}

}
