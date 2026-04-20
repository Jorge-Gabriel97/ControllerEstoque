package br.com.springboot.dao;

import br.com.springboot.model.NotaEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaEntradaDao extends JpaRepository<NotaEntrada, Long> {
}