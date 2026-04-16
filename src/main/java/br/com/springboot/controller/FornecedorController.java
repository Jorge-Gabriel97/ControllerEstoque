package br.com.springboot.controller;

import br.com.springboot.bo.FornecedorBo;
import br.com.springboot.model.Fornecedor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorBo bo;


    @GetMapping("/novo")
    public ModelAndView novo(ModelMap model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return new ModelAndView("/fornecedor/formulario", model);
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Fornecedor fornecedor, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "fornecedor/formulario";
        }
        String mensagemDeSucesso = (fornecedor.getId() == null)
                ? "Fornecedor cadastrado com sucesso!"
                : "Fornecedor atualizado com sucesso!";
        bo.insere(fornecedor);
        attr.addFlashAttribute("feedback", mensagemDeSucesso);
        return "redirect:/fornecedor";
    }

    @GetMapping("")
    public ModelAndView lista(ModelMap model) {
        model.addAttribute("fornecedor", bo.lista());
        return new ModelAndView("/fornecedor/lista", model);
    }

    @GetMapping("/editar/{id}")
    public ModelAndView edita(@PathVariable("id") Long id) {
        ModelMap model = new ModelMap();
        model.addAttribute("fornecedor", bo.pesquisaPeloId(id));
        return new ModelAndView("/fornecedor/formulario", model);
    }

    @GetMapping("/ativar/{id}")
    public String ativar(@PathVariable("id") Long id, RedirectAttributes attr) {
        Fornecedor fornecedor = bo.pesquisaPeloId(id);
        if (fornecedor != null) {
            bo.ativa(fornecedor);
            attr.addFlashAttribute("feedback", "Fornecedor ativado com sucesso!");
        }
        return "redirect:/fornecedor";
    }

    @GetMapping("/inativa/{id}")
    public String inativar(@PathVariable("id") Long id, RedirectAttributes attr) {
        Fornecedor fornecedor = bo.pesquisaPeloId(id);
        if (fornecedor != null) {
            bo.inativa(fornecedor);
            attr.addFlashAttribute("feedback", "Fornecedor inativado com sucesso!");
        }
        return "redirect:/fornecedor";
    }
}