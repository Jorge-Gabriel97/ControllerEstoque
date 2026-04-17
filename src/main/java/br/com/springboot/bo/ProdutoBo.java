package br.com.springboot.bo;

import br.com.springboot.dao.ProdutoDao;
import br.com.springboot.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoBo {

    @Autowired
    private ProdutoDao dao;

    public void insere(Produto produto) {
        dao.save(produto);
    }

    public void atualiza(Produto produto) {
        dao.save(produto);
    }

    public Produto pesquisaPeloId(Long id) {
        return dao.findById(id).orElse(null);
    }

    public List<Produto> lista() {
        return dao.findAll();
    }

    public void ativa(Produto produto) {
        produto.setAtivo(true);
        dao.save(produto);
    }

    public void inativa(Produto produto) {
        produto.setAtivo(false);
        dao.save(produto);
    }
}