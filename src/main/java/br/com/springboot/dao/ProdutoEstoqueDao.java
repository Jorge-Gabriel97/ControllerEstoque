package br.com.springboot.dao;

import br.com.springboot.model.ProdutoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoEstoqueDao extends JpaRepository<ProdutoEstoque, Long> {


}