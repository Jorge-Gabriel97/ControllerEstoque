package br.com.springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="nota_entrada_item")
public class NotaEntradaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Informe a quantidade")
    @Min(value = 1, message = "A quantidade deve ser de no mínimo 1")
    private Integer quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Informe o valor unitário")
    @Min(value = 0, message = "O valor unitário não pode ser negativo")
    private Float valorUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private Float valorTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_entrada_id", nullable = false)
    private NotaEntrada nota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    @NotNull(message = "Informe o produto")
    private Produto produto;

    public NotaEntradaItem() {
    }

    // --- GETTERS E SETTERS ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public NotaEntrada getNota() {
        return nota;
    }

    public void setNota(NotaEntrada nota) {
        this.nota = nota;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}