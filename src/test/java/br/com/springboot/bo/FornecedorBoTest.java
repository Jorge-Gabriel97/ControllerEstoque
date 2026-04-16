package br.com.springboot;

import br.com.springboot.bo.FornecedorBo;
import br.com.springboot.model.Fornecedor;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FornecedorBoTest {

    @Autowired
    private FornecedorBo bo;

    @Test
    @Order(1)
    public void insere() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNomeFantasia("Tech Solutions LTDA");
        fornecedor.setRazaoSocial("Tech Solutions Comércio e Serviços");
        fornecedor.setCnpj("12.345.678/0001-90"); // Usando um CNPJ de teste
        fornecedor.setCelular("(11) 98765-4321");
        fornecedor.setEmail("contato@techsolutions.com.br");
        fornecedor.setAtivo(true);

        bo.insere(fornecedor);

        // As Assertions são melhores que sysout em testes, pois o JUnit valida o resultado automaticamente
        assertNotNull(fornecedor.getId(), "O ID não deveria ser nulo após a inserção.");
        System.out.println("✅ Fornecedor inserido com sucesso! ID gerado: " + fornecedor.getId());
    }

    @Test
    @Order(2)
    public void pesquisaPeloId() {
        // 1. Cria e salva um fornecedor para garantir que ele existe no banco
        Fornecedor fornecedorTeste = new Fornecedor();
        fornecedorTeste.setNomeFantasia("Fornecedor Pesquisa");
        fornecedorTeste.setRazaoSocial("Fornecedor Pesquisa LTDA");
        fornecedorTeste.setCnpj("00.000.000/0001-00");
        bo.insere(fornecedorTeste);

        // 2. Busca o fornecedor pelo ID que acabou de ser gerado
        Fornecedor fornecedorEncontrado = bo.pesquisaPeloId(fornecedorTeste.getId());

        assertNotNull(fornecedorEncontrado, "O fornecedor não foi encontrado pelo ID.");
        assertEquals("Fornecedor Pesquisa", fornecedorEncontrado.getNomeFantasia());
        System.out.println("✅ Fornecedor encontrado: " + fornecedorEncontrado.getNomeFantasia());
    }

    @Test
    @Order(3)
    public void atualiza() {
        // 1. Cria e insere um fornecedor
        Fornecedor fornecedorTeste = new Fornecedor();
        fornecedorTeste.setNomeFantasia("Fornecedor Antigo");
        fornecedorTeste.setRazaoSocial("Antigo LTDA");
        fornecedorTeste.setCnpj("11.111.111/0001-11");
        bo.insere(fornecedorTeste);

        // 2. Busca pelo ID
        Fornecedor fornecedorDoBanco = bo.pesquisaPeloId(fornecedorTeste.getId());

        // 3. Atualiza os dados
        fornecedorDoBanco.setNomeFantasia("Fornecedor Novo");
        fornecedorDoBanco.setCnpj("22.222.222/0001-22");
        bo.atualiza(fornecedorDoBanco);

        // 4. Verifica se a atualização funcionou
        Fornecedor fornecedorAtualizado = bo.pesquisaPeloId(fornecedorTeste.getId());
        assertEquals("Fornecedor Novo", fornecedorAtualizado.getNomeFantasia());
        assertEquals("22.222.222/0001-22", fornecedorAtualizado.getCnpj());

        System.out.println("✅ Fornecedor atualizado com sucesso para: " + fornecedorAtualizado.getNomeFantasia());
    }
}