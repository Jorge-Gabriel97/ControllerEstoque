package br.com.springboot.controller;

import br.com.springboot.bo.NotaEntradaBo;
import br.com.springboot.bo.ProdutoBo;
import br.com.springboot.bo.FornecedorBo;
import br.com.springboot.model.NotaEntrada;
import br.com.springboot.model.Produto;
import br.com.springboot.model.Fornecedor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notas-entrada")
public class NotaEntradaController {

    @Autowired
    private NotaEntradaBo bo;

    @Autowired
    private ProdutoBo produtoBo;

    @Autowired
    private FornecedorBo fornecedorBo;

    @GetMapping("/novo")
    public ResponseEntity<Map<String, Object>> novo() {
        Map<String, Object> model = new HashMap<>();

        // Envia uma nota vazia para ser preenchida
        model.put("nota", new NotaEntrada());

        // Envia listas de produtos e fornecedores ativos para preencher os selects
        List<Produto> produtosAtivos = produtoBo.lista().stream()
                .filter(Produto::isAtivo)
                .collect(Collectors.toList());
        model.put("produtos", produtosAtivos);

        List<Fornecedor> fornecedoresAtivos = fornecedorBo.lista().stream()
                .filter(Fornecedor::isAtivo)
                .collect(Collectors.toList());
        model.put("fornecedores", fornecedoresAtivos);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping("/salvar")
    public ResponseEntity<Map<String, Object>> salvar(@Valid @RequestBody NotaEntrada nota) {
        Map<String, Object> response = new HashMap<>();
        try {
            NotaEntrada notaSalva = bo.salvar(nota);
            response.put("sucesso", "Nota de Entrada inserida com sucesso! ID: " + notaSalva.getId());
            response.put("nota", notaSalva);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            response.put("erro", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("erro", "Ocorreu um erro interno ao salvar a nota.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<NotaEntrada>> lista() {
        List<NotaEntrada> notas = bo.lista();
        return new ResponseEntity<>(notas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaEntrada> pesquisaPeloId(@PathVariable("id") Long id) {
        NotaEntrada nota = bo.pesquisaPeloId(id);
        if (nota != null) {
            return new ResponseEntity<>(nota, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}