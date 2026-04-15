package br.com.springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name="fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Informe o Nome Fantasia")
    @Size(min = 3, max = 100, message = "O Nome Fantasia deve ter entre 3 e 100 caracteres")
    private String nomeFantasia;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Informe a Razão Social")
    @Size(min = 3, max = 100, message = "A Razão Social deve ter entre 3 e 100 caracteres")
    private String razaoSocial;

    @Column(length = 18, unique = true)
    @NotBlank(message = "Informe o CNPJ")
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;

    @Column(length = 15)
    private String celular;

    @Column(length = 100)
    @Email(message = "Formato de e-mail inválido")
    private String email;

    private boolean ativo;


    public Fornecedor() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}