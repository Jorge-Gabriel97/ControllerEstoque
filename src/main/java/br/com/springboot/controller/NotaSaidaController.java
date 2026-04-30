package br.com.springboot.controller;

import br.com.springboot.bo.NotaSaidaBo;
import br.com.springboot.bo.ProdutoBo;
import br.com.springboot.model.NotaSaida;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/nota-saida")
public class NotaSaidaController {

    @Autowired
    private NotaSaidaBo bo;

    @Autowired
    private ProdutoBo produtoBo;

    @GetMapping
    public ModelAndView lista() {
        ModelMap model = new ModelMap();
        model.addAttribute("notas", bo.lista());
        return new ModelAndView("nota-saida/lista", model); // Ajustado para singular conforme seu padrão de pastas
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelMap model = new ModelMap();
        model.addAttribute("notaSaida", new NotaSaida());
        model.addAttribute("produtos", produtoBo.lista()); // Carrega produtos para o Select do formulário
        return new ModelAndView("nota-saida/formulario", model);
    }

    @PostMapping("/salvar")
    public String salvar(@Valid NotaSaida nota, BindingResult result, RedirectAttributes attr, ModelMap model) {
        if (result.hasErrors()) {
            // CRUCIAL: Se der erro, precisamos recarregar a lista de produtos para o formulário não quebrar
            model.addAttribute("produtos", produtoBo.lista());
            return "nota-saida/formulario";
        }

        try {
            bo.salvar(nota);
            attr.addFlashAttribute("feedback", "Venda realizada com sucesso!");
        } catch (RuntimeException e) {
            // Captura o erro de "Estoque Insuficiente" que criamos no BO e devolve para a tela
            model.addAttribute("produtos", produtoBo.lista());
            model.addAttribute("erro", e.getMessage());
            return "nota-saida/formulario";
        }

        return "redirect:/notas-saida";
    }
}