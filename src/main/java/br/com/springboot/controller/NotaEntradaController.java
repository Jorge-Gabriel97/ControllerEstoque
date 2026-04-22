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

@Controller
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

        // Envia uma nota vazia para o Thymeleaf não dar erro ao buscar nota.id
        model.addAttribute("nota", new NotaEntrada());

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
            response.put("sucesso", "Nota de Entrada salva com sucesso! ID: " + notaSalva.getId());
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


    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelMap model = new ModelMap();

        // Busca a nota no banco e manda para a tela
        model.addAttribute("nota", bo.pesquisaPeloId(id));

        // Manda os selects de produtos e fornecedores
        model.addAttribute("produtos", produtoBo.lista().stream().filter(Produto::isAtivo).collect(Collectors.toList()));
        model.addAttribute("fornecedores", fornecedorBo.lista().stream().filter(Fornecedor::isAtivo).collect(Collectors.toList()));

        return new ModelAndView("notas-entrada/formulario", model);
    }

    // --- REMOVE UMA NOTA INTEIRA ---
    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id) {
        NotaEntrada nota = bo.pesquisaPeloId(id);
        bo.remove(nota); // O Hibernate vai deletar a nota e, em cascata, todos os itens dela!
        return "redirect:/notas-entrada";
    }

    // --- LISTA TODAS AS NOTAS ---
    @GetMapping
    public ModelAndView lista() {
        ModelMap model = new ModelMap();
        model.addAttribute("notas", bo.lista());
        return new ModelAndView("notas-entrada/lista", model);
    }
}