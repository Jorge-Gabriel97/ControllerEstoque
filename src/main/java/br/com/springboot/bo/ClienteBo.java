package br.com.springboot.bo;

import br.com.springboot.dao.CRUD;
import br.com.springboot.dao.ClienteDao;
import br.com.springboot.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importação necessária

import java.util.List;

@Service
public class ClienteBo implements CRUD<Cliente, Long> {

    @Autowired
    private ClienteDao dao;

    @Override
    public Cliente pesquisaPeloId(Long id) {
        return dao.pesquisaPeloId(id);
    }

    @Override
    public List<Cliente> lista() {
        return dao.lista();
    }

    @Override
    @Transactional // Abre a transação para inserir
    public void insere(Cliente cliente) {
        dao.insere(cliente);
    }

    @Override
    @Transactional // Abre a transação para atualizar
    public void atualiza(Cliente cliente) {
        dao.atualiza(cliente);
    }

    @Override
    @Transactional // Abre a transação para remover
    public void remove(Cliente cliente) {
        dao.remove(cliente);
    }

    @Transactional // Abre a transação para atualizar (inativar)
    public void inativa(Cliente cliente) {
        cliente.setAtivo(false);
        dao.atualiza(cliente);
    }

    @Transactional // Abre a transação para atualizar (ativar)
    public void ativa(Cliente cliente){
        cliente.setAtivo(true);
        dao.atualiza(cliente);
    }
}