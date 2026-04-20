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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller //
@RequestMapping("/notas-entrada")
public class NotaEntradaController {

    @Autowired
    private NotaEntradaBo bo;

    @Autowired
    private ProdutoBo produtoBo;

    @Autowired
    private FornecedorBo fornecedorBo;


    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelMap model = new ModelMap();

        // Filtra e envia apenas produtos e fornecedores ativos para a tela
        List<Produto> produtosAtivos = produtoBo.lista().stream()
                .filter(Produto::isAtivo)
                .collect(Collectors.toList());
        model.addAttribute("produtos", produtosAtivos);

        List<Fornecedor> fornecedoresAtivos = fornecedorBo.lista().stream()
                .filter(Fornecedor::isAtivo)
                .collect(Collectors.toList());
        model.addAttribute("fornecedores", fornecedoresAtivos);


        return new ModelAndView("notas-entrada/formulario", model);
    }


    @PostMapping("/salvar")
    @ResponseBody
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
    public ModelAndView lista() {
        ModelMap model = new ModelMap();
        model.addAttribute("notas", bo.lista());
        return new ModelAndView("notas-entrada/lista", model);
    }
}