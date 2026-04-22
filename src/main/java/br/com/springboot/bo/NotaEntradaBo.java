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

        // --- 1. REGRAS DE CABEÇALHO (NOVA NOTA vs EDIÇÃO) ---
        if (nota.getId() == null) {
            // Se não tem ID, é uma NOVA nota: Seta a data de agora
            nota.setDataHora(LocalDateTime.now());

            // Validação de Duplicidade (Mesmo fornecedor no mesmo dia)
            boolean existeDuplicidade = dao.findAll().stream()
                    .anyMatch(n ->
                            n.getFornecedor().getId().equals(nota.getFornecedor().getId()) &&
                                    n.getDataHora().toLocalDate().equals(nota.getDataHora().toLocalDate())
                    );

            if (existeDuplicidade) {
                throw new RuntimeException("Atenção: Já existe uma nota registrada hoje para este fornecedor. Evite duplicidade.");
            }
        } else {
            // Se tem ID, é EDIÇÃO: Precisamos resgatar a data original do banco para não perder
            NotaEntrada notaAntiga = pesquisaPeloId(nota.getId());
            if (notaAntiga != null) {
                nota.setDataHora(notaAntiga.getDataHora());
            }
        }

        // --- 2. CÁLCULO E VALIDAÇÃO DOS ITENS ---
        Float totalNota = 0f;
        List<NotaEntradaItem> itens = nota.getItens();

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

            Float valorTotalItem = item.getQuantidade() * item.getValorUnitario();
            item.setValorTotal(valorTotalItem);

            // Soma ao grande total da nota
            totalNota += valorTotalItem;
        }

        // 3. Atribui o total da nota recalculado com segurança
        nota.setTotal(totalNota);

        // 4. Salva a nota (e seus itens em cascata) no banco
        return dao.save(nota);
    }

    public List<NotaEntrada> lista() {
        return dao.findAll();
    }

    public NotaEntrada pesquisaPeloId(Long id) {
        return dao.findById(id).orElse(null);
    }

    public void remove(NotaEntrada nota) {
        // Correção: Agora ele realmente deleta a nota do banco!
        dao.delete(nota);
    }
}