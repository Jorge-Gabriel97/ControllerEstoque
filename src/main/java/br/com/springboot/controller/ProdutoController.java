package br.com.springboot.controller;

import br.com.springboot.bo.ProdutoBo;
import br.com.springboot.model.Categoria;
import br.com.springboot.model.Produto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/produtos") // Sempre no plural!
public class ProdutoController {

    @Autowired
    private ProdutoBo bo;

    @GetMapping
    public ModelAndView lista(ModelMap model) {
        model.addAttribute("produtos", bo.lista());
        return new ModelAndView("produto/lista", model);
    }

    @GetMapping("/novo")
    public ModelAndView novo(ModelMap model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", Categoria.values()); // Envia os Enums para a View
        return new ModelAndView("produto/formulario", model);
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Produto produto, BindingResult result, RedirectAttributes attr, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", Categoria.values());
            return "produto/formulario";
        }

        String mensagemDeSucesso = (produto.getId() == null)
                ? "Produto cadastrado com sucesso!"
                : "Produto atualizado com sucesso!";

        // 👇 CORREÇÃO: Usando o método que existe no BO
        bo.salvar(produto);

        attr.addFlashAttribute("feedback", mensagemDeSucesso);
        return "redirect:/produtos";
    }

    @GetMapping("/ativar/{id}")
    public String ativar(@PathVariable("id") Long id, RedirectAttributes attr) {
        Produto produto = bo.pesquisaPeloId(id);
        if (produto != null) {
            // 👇 CORREÇÃO: Ativa e usa o salvar()
            produto.setAtivo(true);
            bo.salvar(produto);
            attr.addFlashAttribute("feedback", "Produto ativado com sucesso!");
        }
        return "redirect:/produtos";
    }

    @GetMapping("/inativa/{id}")
    public String inativar(@PathVariable("id") Long id, RedirectAttributes attr) {
        Produto produto = bo.pesquisaPeloId(id);
        if (produto != null) {
            // 👇 CORREÇÃO: Inativa e usa o salvar()
            produto.setAtivo(false);
            bo.salvar(produto);
            attr.addFlashAttribute("feedback", "Produto inativado com sucesso!");
        }
        return "redirect:/produtos";
    }
}