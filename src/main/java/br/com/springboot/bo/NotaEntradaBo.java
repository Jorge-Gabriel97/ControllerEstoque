package br.com.springboot.bo;

import br.com.springboot.dao.NotaEntradaDao;
import br.com.springboot.model.NotaEntrada;
import br.com.springboot.model.NotaEntradaItem;
import br.com.springboot.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotaEntradaBo {

    @Autowired
    private NotaEntradaDao dao;

    @Autowired
    private ProdutoBo produtoBo;

    @Autowired
    private FornecedorBo fornecedorBo;

    @Transactional
    public NotaEntrada salvar(NotaEntrada nota) {
        // 1. Atribui a data/hora atual
        nota.setDataHora(LocalDateTime.now());

        Float totalNota = 0f;
        List<NotaEntradaItem> itens = nota.getItens();

        // 2. Itera sobre os itens da nota
        for (NotaEntradaItem item : itens) {
            // Garante o relacionamento bidirecional (item sabe qual é a nota dele)
            item.setNota(nota);

            // Valida e pesquisa o produto
            if (item.getProduto() != null && item.getProduto().getId() != null) {
                Produto produto = produtoBo.pesquisaPeloId(item.getProduto().getId());
                if (produto != null) {
                    item.setProduto(produto);

                    // Valida se o produto está ativo para movimentação
                    if (!produto.isAtivo()) {
                        throw new RuntimeException("Não é possível inserir o produto '" + produto.getNome() + "', pois ele está inativo.");
                    }
                } else {
                    throw new RuntimeException("Produto com ID " + item.getProduto().getId() + " não encontrado.");
                }
            } else {
                throw new RuntimeException("Informe o produto em todos os itens da nota.");
            }

            // 3. Calcula o valor total do item (quantidade * valorUnitario)
            Float valorTotalItem = item.getQuantidade() * item.getValorUnitario();
            item.setValorTotal(valorTotalItem);

            // Soma ao total da nota
            totalNota += valorTotalItem;
        }

        // 4. Atribui o total da nota calculado
        nota.setTotal(totalNota);

        // 5. Salva a nota (e seus itens) no banco
        return dao.save(nota);
    }

    public List<NotaEntrada> lista() {
        return dao.findAll();
    }

    public NotaEntrada pesquisaPeloId(Long id) {
        return dao.findById(id).orElse(null);
    }
}