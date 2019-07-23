package br.com.tt.petshop.controller;

import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Cliente;
import br.com.tt.petshop.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sistema", "PETSHOP");
        model.addAttribute("clientes", clienteService.listar());
        return "index";
    }

    @GetMapping("/cliente-adicionar")
    public String paginaAdicionar(Model model){
        return "cliente-adicionar";
    }

    @PostMapping("/cliente-form")
    public RedirectView clientForm(Cliente cli, Model model) {
        try {
            clienteService.adicionar(cli);
        } catch (BusinessException e) {
            model.addAttribute("erro: ", e.getMessage());
        }
        return new RedirectView("/");
    }

    @GetMapping("/cliente-excluir")
    public RedirectView clienteExcluir(@RequestParam Long id){
        clienteService.remover(id);
        return new RedirectView("/");
    }

}
