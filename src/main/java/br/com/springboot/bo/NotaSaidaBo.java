package br.com.springboot.bo;

import br.com.springboot.dao.NotaSaidaDao;
import br.com.springboot.model.NotaSaida;
import br.com.springboot.model.NotaSaidaItem;
import br.com.springboot.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotaSaidaBo {

    @Autowired
    private NotaSaidaDao dao;

    @Autowired
    private ProdutoBo produtoBo;

    @Transactional
    public NotaSaida salvar(NotaSaida nota) {
        boolean isNovaNota = (nota.getId() == null);

        if (isNovaNota) {
            nota.setDataHora(LocalDateTime.now());
        }

        Double totalAcumulado = 0.0;
        List<NotaSaidaItem> itens = nota.getItens();

        if (itens != null) {
            for (NotaSaidaItem item : itens) {
                item.setNota(nota);

                if (item.getProduto() != null && item.getProduto().getId() != null) {
                    Produto produto = produtoBo.pesquisaPeloId(item.getProduto().getId());

                    if (produto == null) throw new RuntimeException("Produto não encontrado.");
                    if (!produto.isAtivo()) throw new RuntimeException("O produto '" + produto.getNome() + "' está inativo.");

                    // TRIGGER DE SAÍDA: Subtrai do estoque e valida saldo
                    if (isNovaNota) {
                        int saldoAtual = produto.getQuantidade();
                        int quantidadeVendida = item.getQuantidade();

                        if (saldoAtual < quantidadeVendida) {
                            throw new RuntimeException("Saldo insuficiente para o produto: " + produto.getNome());
                        }

                        produto.setQuantidade(saldoAtual - quantidadeVendida);
                        produtoBo.salvar(produto);
                    }
                    item.setProduto(produto);
                }

                Double valorTotalItem = (double) (item.getQuantidade() * item.getValorUnitario());
                item.setValorTotal(valorTotalItem.floatValue());
                totalAcumulado += valorTotalItem;
            }
        }

        nota.setTotal(totalAcumulado.floatValue());
        return dao.save(nota);
    }

    public List<NotaSaida> lista() { return dao.findAll(); }
    public NotaSaida pesquisaPeloId(Long id) { return dao.findById(id).orElse(null); }

    @Transactional
    public void remove(NotaSaida nota) {
        NotaSaida notaNoBanco = pesquisaPeloId(nota.getId());
        if (notaNoBanco != null) {
            // DEVOLUÇÃO AO ESTOQUE: Se excluir a venda, o produto volta para a prateleira
            for (NotaSaidaItem item : notaNoBanco.getItens()) {
                Produto p = item.getProduto();
                p.setQuantidade(p.getQuantidade() + item.getQuantidade());
                produtoBo.salvar(p);
            }
            dao.delete(notaNoBanco);
        }
    }
}