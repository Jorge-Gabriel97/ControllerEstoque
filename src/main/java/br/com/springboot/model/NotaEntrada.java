package br.com.springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="nota_entrada")
public class NotaEntrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private Float Total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    @NotNull(message = "Informe o fornecedor")
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "nota", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty(message = "A nota deve possuir pelo menos um item")
    private List<NotaEntradaItem> itens = new ArrayList<>();

    public NotaEntrada() {
    }

    // --- GETTERS E SETTERS ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Float getTotal() {
        return Total;
    }

    public void setTotal(Float total) {
        this.Total = total;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<NotaEntradaItem> getItens() {
        return itens;
    }

    public void setItens(List<NotaEntradaItem> itens) {
        this.itens = itens;
    }
}