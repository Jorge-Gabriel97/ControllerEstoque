package br.com.springboot.bo;

import br.com.springboot.dao.ProdutoEstoqueDao;
import br.com.springboot.model.ProdutoEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoEstoqueBo {

    @Autowired
    private ProdutoEstoqueDao dao;

    // --- SALVAR OU ATUALIZAR ---
    public ProdutoEstoque salvar(ProdutoEstoque produto) {
        // Aqui você pode adicionar regras de negócio antes de salvar.
        // Exemplo: if (produto.getPreco() < 0) throw new IllegalArgumentException("Preço inválido");
        return dao.save(produto);
    }

    // --- BUSCAR POR ID ---
    public ProdutoEstoque pesquisaPeloId(Long id) {
        Optional<ProdutoEstoque> produtoEncontrado = dao.findById(id);
        if (produtoEncontrado.isPresent()) {
            return produtoEncontrado.get();
        }
        throw new RuntimeException("Produto com ID " + id + " não encontrado.");
    }

    // --- LISTAR TODOS ---
    public List<ProdutoEstoque> lista() {
        return dao.findAll();
    }

    // --- REMOVER ---
    public void remove(ProdutoEstoque produto) {
        dao.delete(produto);
    }

    // --- ATUALIZAR ESTOQUE (Regra de Negócio Específica) ---
    public ProdutoEstoque atualizarEstoque(Long id, Integer quantidadeAdicional) {
        ProdutoEstoque produto = pesquisaPeloId(id);

        int novaQuantidade = produto.getQuantidadeEmEstoque() + quantidadeAdicional;

        if (novaQuantidade < 0) {
            throw new RuntimeException("Estoque insuficiente para realizar esta operação.");
        }

        produto.setQuantidadeEmEstoque(novaQuantidade);
        return dao.save(produto);
    }
}