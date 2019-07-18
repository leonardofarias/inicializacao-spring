package br.com.tt.petshop.controller;

import br.com.tt.petshop.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
