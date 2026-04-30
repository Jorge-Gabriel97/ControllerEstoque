package br.com.springboot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notas_saida")
public class NotaSaida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    private Float total;

    // Relacionamento Mestre-Detalhe com Cascade Total
    @OneToMany(mappedBy = "nota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotaSaidaItem> itens;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public Float getTotal() { return total; }
    public void setTotal(Float total) { this.total = total; }
    public List<NotaSaidaItem> getItens() { return itens; }
    public void setItens(List<NotaSaidaItem> itens) { this.itens = itens; }
}