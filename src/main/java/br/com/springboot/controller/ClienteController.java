package br.com.springboot.controller;

import br.com.springboot.bo.ClienteBo;
import br.com.springboot.model.Cliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteBo bo;

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model) {
        model.addAttribute("cliente", new Cliente());
        return new ModelAndView("/cliente/formulario", model);
    }


    @PostMapping("/salvar")
    public String salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "cliente/formulario";
        }
        String mensagemDeSucesso = (cliente.getId() == null)
                ? "Cliente cadastrado com sucesso!"
                : "Cliente atualizado com sucesso!";
        bo.insere(cliente);
        attr.addFlashAttribute("feedback", mensagemDeSucesso);
        return "redirect:/clientes";
    }

    @GetMapping("")
    public ModelAndView lista(ModelMap model) {
        model.addAttribute("clientes", bo.lista());
        return new ModelAndView("/cliente/lista", model);
    }

    @GetMapping("/editar/{id}")
    public ModelAndView edita(@PathVariable("id") Long id) {
        ModelMap model = new ModelMap();
        model.addAttribute("cliente", bo.pesquisaPeloId(id));
        return new ModelAndView("/cliente/formulario", model);
    }

    @GetMapping("/ativar/{id}")
    public String ativar(@PathVariable("id") Long id, RedirectAttributes attr) {
        Cliente cliente = bo.pesquisaPeloId(id);
        if (cliente != null) {
            bo.ativa(cliente);
            attr.addFlashAttribute("feedback", "Cliente ativado com sucesso!");
        }
        return "redirect:/clientes";
    }

    @GetMapping("/inativa/{id}")
    public String inativar(@PathVariable("id") Long id, RedirectAttributes attr) {
        Cliente cliente = bo.pesquisaPeloId(id);
        if (cliente != null) {
            bo.inativa(cliente);
            attr.addFlashAttribute("feedback", "Cliente inativado com sucesso!");
        }
        return "redirect:/clientes";
    }
}