package br.com.springboot.dao;

import br.com.springboot.model.NotaEntradaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaEntradaItemDao extends JpaRepository<NotaEntradaItem, Long> {
}