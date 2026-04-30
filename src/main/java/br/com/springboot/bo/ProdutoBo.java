package br.com.springboot.bo;

import br.com.springboot.dao.ProdutoDao;
import br.com.springboot.model.Produto;
import br.com.springboot.model.ProdutoEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoBo {

    @Autowired
    private ProdutoDao dao;

    @Autowired
    private ProdutoEstoqueBo produtoEstoqueBo;

    public void salvar(Produto produto) {
        boolean isNovoProduto = (produto.getId() == null);
        dao.save(produto);

        if (isNovoProduto) {
            ProdutoEstoque novoEstoque = new ProdutoEstoque();
            novoEstoque.setNome(produto.getNome());
            novoEstoque.setDescricao("Adicionado automaticamente via cadastro de produto.");
            novoEstoque.setPreco(0.0);
            novoEstoque.setQuantidadeEmEstoque(produto.getQuantidade());
            produtoEstoqueBo.salvar(novoEstoque);
        }
    }

    public Produto pesquisaPeloId(Long id) {
        return dao.findById(id).orElse(null);
    }

    public List<Produto> lista() {
        return dao.findAll();
    }
}