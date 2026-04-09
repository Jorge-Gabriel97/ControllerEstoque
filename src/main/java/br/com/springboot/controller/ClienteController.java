package br.com.springboot.controller;

import br.com.springboot.bo.ClienteBo;
import br.com.springboot.model.Cliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteBo bo;

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(ModelMap model){
        model.addAttribute("cliente", new Cliente());
        return new ModelAndView("/cliente/formulario", model);
    }


    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String salvar(@Valid Cliente cliente, BindingResult result) {
        if (result.hasErrors())
            return "cliente/formulario";
        bo.insere(cliente);
        return "redirect:/clientes/novo";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView lista(ModelMap model){
        model.addAttribute("clientes", bo.lista());
        return new ModelAndView("/cliente/lista", model);
    }
    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") Long id) {
        Cliente cliente = bo.pesquisaPeloId(id);
        ModelMap model = new ModelMap();
        model.addAttribute("cliente", bo.pesquisaPeloId(id));
        return new ModelAndView("/cliente/formulario", model);
    }
    @RequestMapping(value = "/ativar/{id}", method = RequestMethod.GET)
    public String ativar(@PathVariable("id") Long id) {
        Cliente cliente = bo.pesquisaPeloId(id);
        bo.ativa(cliente);
        return "redirect:/clientes";
    }

    @RequestMapping(value = "/inativa/{id}", method = RequestMethod.GET)
    public String inativar(@PathVariable("id") Long id) {
        Cliente cliente = bo.pesquisaPeloId(id);
        if (cliente != null) {
            bo.inativa(cliente); }
        return "redirect:/clientes";
    }
}