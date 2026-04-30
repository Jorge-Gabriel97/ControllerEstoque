package br.com.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "nota_saida_itens")
public class NotaSaidaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nota_saida_id")
    private NotaSaida nota;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Integer quantidade;

    private Float valorUnitario;

    private Float valorTotal;

    // --- GETTERS E SETTERS CORRIGIDOS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public NotaSaida getNota() { return nota; }
    public void setNota(NotaSaida nota) { this.nota = nota; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Float getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(Float valorUnitario) { this.valorUnitario = valorUnitario; }

    public Float getValorTotal() { return valorTotal; }

    // Correção da Linha 38: Agora o parâmetro e a atribuição estão corretos
    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }
}