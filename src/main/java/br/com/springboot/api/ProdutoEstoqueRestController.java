package br.com.springboot.api;

import br.com.springboot.bo.ProdutoEstoqueBo;
import br.com.springboot.model.ProdutoEstoque;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class ProdutoEstoqueRestController {

    @Autowired
    private ProdutoEstoqueBo produtoEstoqueBo;


    @GetMapping
    public ResponseEntity<List<ProdutoEstoque>> listaTodos() {
        List<ProdutoEstoque> lista = produtoEstoqueBo.lista();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> pesquisaPeloId(@PathVariable("id") Long id) {
        try {
            ProdutoEstoque produto = produtoEstoqueBo.pesquisaPeloId(id);
            return new ResponseEntity<>(produto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody ProdutoEstoque produto) {
        try {
            // O Spring converte o JSON que vem na requisição num objeto ProdutoEstoque (por causa do @RequestBody)
            ProdutoEstoque produtoSalvo = produtoEstoqueBo.salvar(produto);
            // Retorna o produto recém-criado com status 201 (Created)
            return new ResponseEntity<>(produtoSalvo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar produto: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @Valid @RequestBody ProdutoEstoque produtoAtualizado) {
        try {
            // 1. Primeiro verificamos se o produto realmente existe no banco usando a regra do BO
            ProdutoEstoque produtoExistente = produtoEstoqueBo.pesquisaPeloId(id);

            // 2. Transferimos os dados novos para o objeto existente, mas MANTENDO O MESMO ID
            produtoExistente.setNome(produtoAtualizado.getNome());
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
            produtoExistente.setPreco(produtoAtualizado.getPreco());
            produtoExistente.setQuantidadeEmEstoque(produtoAtualizado.getQuantidadeEmEstoque());

            // 3. Mandamos salvar de volta
            ProdutoEstoque produtoSalvo = produtoEstoqueBo.salvar(produtoExistente);
            return new ResponseEntity<>(produtoSalvo, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar produto: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable("id") Long id) {
        try {
            // Verifica se existe antes de deletar
            ProdutoEstoque produto = produtoEstoqueBo.pesquisaPeloId(id);
            produtoEstoqueBo.remove(produto);
            // Se deletou com sucesso, não precisa retornar o objeto, apenas um OK
            return new ResponseEntity<>("Produto removido com sucesso.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/{id}/adicionar-estoque/{quantidade}")
    public ResponseEntity<?> adicionarEstoque(@PathVariable("id") Long id, @PathVariable("quantidade") Integer quantidade) {
        try {
            ProdutoEstoque produtoAtualizado = produtoEstoqueBo.atualizarEstoque(id, quantidade);
            return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}