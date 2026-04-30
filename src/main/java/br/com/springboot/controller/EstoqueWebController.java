package br.com.springboot.controller;

import br.com.springboot.bo.ProdutoEstoqueBo;
import br.com.springboot.model.ProdutoEstoque;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/estoque")
public class EstoqueWebController {

    @Autowired
    private ProdutoEstoqueBo produtoEstoqueBo;


    @GetMapping
    public ModelAndView lista() {
        ModelMap model = new ModelMap();
        model.addAttribute("produtos", produtoEstoqueBo.lista());
        return new ModelAndView("estoque/lista", model);
    }


    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelMap model = new ModelMap();
        model.addAttribute("produtoEstoque", new ProdutoEstoque());
        return new ModelAndView("estoque/formulario", model);
    }


    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid @ModelAttribute("produtoEstoque") ProdutoEstoque produtoEstoque, BindingResult result) {
        // Se houver erro de validação (ex: preço nulo), devolve para a tela do formulário para mostrar o erro
        if (result.hasErrors()) {
            return new ModelAndView("estoque/formulario");
        }

        produtoEstoqueBo.salvar(produtoEstoque);


        return new ModelAndView("redirect:/estoque");
    }


    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelMap model = new ModelMap();
        // Pega o produto do banco e manda para a tela. O formulário vai vir preenchido!
        model.addAttribute("produtoEstoque", produtoEstoqueBo.pesquisaPeloId(id));
        return new ModelAndView("estoque/formulario", model);
    }


    @GetMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        ProdutoEstoque produto = produtoEstoqueBo.pesquisaPeloId(id);
        produtoEstoqueBo.remove(produto);
        return new ModelAndView("redirect:/estoque");
    }
}