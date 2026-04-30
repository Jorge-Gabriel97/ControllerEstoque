package br.com.springboot;

import br.com.springboot.bo.ProdutoEstoqueBo;
import br.com.springboot.model.ProdutoEstoque;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "ADMIN_USER=admin",
        "ADMIN_PASS=71991408285",
        "DB_USER=root",
        "DB_PASS=wN90H;4eL{rj(ckP-D9",
        "DB_URL=jdbc:mysql://localhost:3306/mySql"
})
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoEstoqueBoTest {

    @Autowired
    private ProdutoEstoqueBo bo;

    @Test
    @Order(1)
    public void salvar() {
        ProdutoEstoque produto = new ProdutoEstoque();
        produto.setNome("Teclado Mecânico RGB");
        produto.setDescricao("Teclado switch blue padrão ABNT2");
        produto.setPreco(250.00);
        produto.setQuantidadeEmEstoque(15);

        bo.salvar(produto);

        assertNotNull(produto.getId());
        System.out.println("✅ Salvar: Produto inserido com ID: " + produto.getId());
    }

    @Test
    @Order(2)
    public void pesquisaPeloId() {
        ProdutoEstoque produto = new ProdutoEstoque();
        produto.setNome("Monitor 24 Polegadas");
        produto.setDescricao("Monitor Full HD 75Hz");   // ← adicionar
        produto.setPreco(899.90);                        // ← adicionar
        produto.setQuantidadeEmEstoque(5);
        bo.salvar(produto);

        ProdutoEstoque encontrado = bo.pesquisaPeloId(produto.getId());

        assertNotNull(encontrado);
        assertEquals("Monitor 24 Polegadas", encontrado.getNome());
        System.out.println("✅ Pesquisa: Produto '" + encontrado.getNome() + "' encontrado.");
    }

    @Test
    @Order(3)
    public void atualizarEstoque() {
        ProdutoEstoque produto = new ProdutoEstoque();
        produto.setNome("Mouse Gamer");
        produto.setDescricao("Mouse 6400 DPI com RGB");  // ← adicionar
        produto.setPreco(150.00);                         // ← adicionar
        produto.setQuantidadeEmEstoque(10);
        bo.salvar(produto);

        bo.atualizarEstoque(produto.getId(), 5);

        ProdutoEstoque atualizado = bo.pesquisaPeloId(produto.getId());
        assertEquals(15, atualizado.getQuantidadeEmEstoque());
        System.out.println("✅ Regra de Negócio: Estoque atualizado para " + atualizado.getQuantidadeEmEstoque());
    }

    @Test
    @Order(4)
    public void validarEstoqueInsuficiente() {
        ProdutoEstoque produto = new ProdutoEstoque();
        produto.setNome("Headset USB");
        produto.setDescricao("Headset estéreo com microfone");  // ← adicionar
        produto.setPreco(199.90);                                // ← adicionar
        produto.setQuantidadeEmEstoque(2);
        bo.salvar(produto);

        assertThrows(RuntimeException.class, () -> {
            bo.atualizarEstoque(produto.getId(), -5);
        });
        System.out.println("✅ Validação: Sistema impediu estoque negativo corretamente.");
    }
}