package br.com.springboot.dao;

import br.com.springboot.model.NotaSaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaSaidaDao extends JpaRepository<NotaSaida, Long> {
    // O Spring gera automaticamente os métodos de CRUD!
}