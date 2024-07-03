package com.myteste.sellfree.servicos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myteste.sellfree.modelo.Produto;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {
	List<Produto> findByNomeContaining(String nome);
    List<Produto> findByBrandContaining(String brand);
	
	
	
}
