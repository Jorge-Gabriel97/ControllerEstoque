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

    @Transactional
    public NotaEntrada salvar(NotaEntrada nota) {
        boolean isNovaNota = (nota.getId() == null);

        if (isNovaNota) {
            nota.setDataHora(LocalDateTime.now());
        } else {
            NotaEntrada notaAntiga = pesquisaPeloId(nota.getId());
            if (notaAntiga != null) {
                nota.setDataHora(notaAntiga.getDataHora());
            }
        }

        Double totalAcumulado = 0.0;
        List<NotaEntradaItem> itens = nota.getItens();

        if (itens != null) {
            for (NotaEntradaItem item : itens) {
                item.setNota(nota);

                if (item.getProduto() != null && item.getProduto().getId() != null) {
                    Produto produto = produtoBo.pesquisaPeloId(item.getProduto().getId());

                    if (produto == null) {
                        throw new RuntimeException("Produto não encontrado.");
                    }

                    if (!produto.isAtivo()) {
                        throw new RuntimeException("O produto '" + produto.getNome() + "' está inativo.");
                    }

                    if (isNovaNota) {
                        produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
                        produtoBo.salvar(produto);
                    }

                    item.setProduto(produto);
                }

                double valorUnitario = item.getValorUnitario() != null ? item.getValorUnitario().doubleValue() : 0.0;
                double qtd = item.getQuantidade() != null ? item.getQuantidade().doubleValue() : 0.0;
                Double valorTotalItem = valorUnitario * qtd;

                item.setValorTotal(valorTotalItem.floatValue());
                totalAcumulado += valorTotalItem;
            }
        }

        nota.setTotal(totalAcumulado.floatValue());
        return dao.save(nota);
    }

    @Transactional
    public void remove(NotaEntrada nota) {
        NotaEntrada notaNoBanco = pesquisaPeloId(nota.getId());

        if (notaNoBanco != null) {
            for (NotaEntradaItem item : notaNoBanco.getItens()) {
                Produto produto = item.getProduto();
                if (produto != null) {
                    produto.setQuantidade(Math.max(0, produto.getQuantidade() - item.getQuantidade()));
                    produtoBo.salvar(produto);
                }
            }
            dao.delete(notaNoBanco);
        }
    }

    public List<NotaEntrada> lista() {
        return dao.findAll();
    }

    public NotaEntrada pesquisaPeloId(Long id) {
        return dao.findById(id).orElse(null);
    }
}