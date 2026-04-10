package br.com.springboot.dao;

import br.com.springboot.model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ClienteDao implements CRUD<Cliente, Long> {

    @PersistenceContext
    private EntityManager entityManagerger;

    @Override
    public Cliente pesquisaPeloId(Long id) {
        return entityManagerger.find(Cliente.class, id);
    }

    @Override
    public List<Cliente> lista() {
        Query query = entityManagerger.createQuery("Select c from Cliente c");
        return (List<Cliente>) query.getResultList();
    }

    @Override
    public void insere(Cliente cliente) {
        if (cliente.getId() == null) {
            entityManagerger.persist(cliente);
        } else {
            entityManagerger.merge(cliente);
        }
    }

    @Override
    public void atualiza(Cliente cliente) {
        entityManagerger.merge(cliente);
    }

    @Override
    public void remove(Cliente cliente) {
        entityManagerger.remove(cliente);
    }
}