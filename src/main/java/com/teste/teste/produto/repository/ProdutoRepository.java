package com.teste.teste.produto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.teste.teste.categoria.entity.CategoriaEntity;
import com.teste.teste.produto.entity.ProdutoEntity;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>, JpaSpecificationExecutor<ProdutoEntity> {

	List<ProdutoEntity> findByCategoria(CategoriaEntity categoria);

}