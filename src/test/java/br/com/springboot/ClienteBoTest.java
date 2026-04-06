package br.com.springboot;

import br.com.springboot.bo.ClienteBo;
import br.com.springboot.model.Cliente;
import br.com.springboot.model.Sexo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class ClienteBoTest {

    @Autowired
    private ClienteBo bo;

    @Test
    @Order(1)
    public void insere(){
        Cliente cliente = new Cliente();
        cliente.setNome("Jorge Gabriel");
        cliente.setCpf("12345678");
        cliente.setDataDeNascimento(LocalDate.of(1997, 5, 17));
        cliente.setSexo(Sexo.MASCULINO);
        cliente.setCelular("32342343252");
        cliente.setTelefone("3243423423");
        cliente.setEmail("jorge@email.com.br");
        cliente.setAtivo(true);

        bo.insere(cliente);
        System.out.println("✅ Cliente inserido com sucesso! ID gerado: " + cliente.getId());
    }

    @Test
    @Order(2)
    public void pesquisaPeloId(){

        Cliente clienteTeste = new Cliente();
        clienteTeste.setNome("Cliente Pesquisa");
        bo.insere(clienteTeste);


        Cliente clienteEncontrado = bo.pesquisaPeloId(clienteTeste.getId());
        System.out.println("✅ Cliente encontrado: " + clienteEncontrado.getNome());
    }

    @Test
    @Order(3)
    public void atualiza(){

        Cliente clienteTeste = new Cliente();
        clienteTeste.setNome("Cliente Desatualizado");
        bo.insere(clienteTeste);

        // 2. Buscamos ele pelo ID dinâmico
        Cliente clienteDoBanco = bo.pesquisaPeloId(clienteTeste.getId());

        // 3. Modificamos e salvamos
        clienteDoBanco.setCpf("234234234");
        bo.atualiza(clienteDoBanco);

        System.out.println("✅ Cliente atualizado com sucesso!");
    }
}