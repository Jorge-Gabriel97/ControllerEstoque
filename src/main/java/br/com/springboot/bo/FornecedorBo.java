package br.com.springboot.bo;

import br.com.springboot.dao.CRUD;
import br.com.springboot.dao.FornecedorDao;
import br.com.springboot.model.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FornecedorBo implements CRUD<Fornecedor, Long> {

    @Autowired
    private FornecedorDao dao;

    @Override
    public Fornecedor pesquisaPeloId(Long id) {
        return dao.pesquisaPeloId(id);
    }

    @Override
    public List<Fornecedor> lista() {
        return dao.lista();
    }

    @Override
    public void insere(Fornecedor fornecedor) {
        dao.insere(fornecedor);
    }

    @Override
    public void atualiza(Fornecedor fornecedor) {
        dao.atualiza(fornecedor);
    }

    @Override
    public void remove(Fornecedor fornecedor) {
        dao.remove(fornecedor);
    }
    @Transactional
    public void inativa(Fornecedor fornecedor) {
        fornecedor.setAtivo(false);
        dao.atualiza(fornecedor);
    }

    @Transactional
    public void ativa(Fornecedor fornecedor) {
        fornecedor.setAtivo(true);
        dao.atualiza(fornecedor);
    }
}
