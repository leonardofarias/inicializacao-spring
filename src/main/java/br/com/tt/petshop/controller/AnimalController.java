package br.com.tt.petshop.controller;

import br.com.tt.petshop.enums.EspecieEnum;
import br.com.tt.petshop.exception.BusinessException;
import br.com.tt.petshop.model.Animal;
import br.com.tt.petshop.service.AnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/animais-listar")
    public String listar(Model model, @RequestParam Long clientId) {
        model.addAttribute("sistema", "PETSHOP");
        model.addAttribute("clientId", clientId);
        model.addAttribute("animais", animalService.listar(clientId));
        return "animais-listar";
    }

    @GetMapping("/animal-adicionar")
    public String paginaAdicionar(Model model) {
        model.addAttribute("especies", listarEspecies());
        return "animal-adicionar";
    }

    private List<EspecieEnum> listarEspecies(){
        return animalService.listarEspecies();
    }

    @PostMapping("/animal-form")
    public RedirectView animalForm(Animal animal, Model model) {
        try {
            animalService.adicionar(animal);
        } catch (BusinessException e) {
            model.addAttribute("erro: ", e.getMessage());
        }
        return new RedirectView("/animais-listar?clientId=" + animal.getClientId());
    }

    @GetMapping("/animal-excluir")
    public RedirectView clienteExcluir(@RequestParam Long clientId, @RequestParam String nome){
        Animal animal = new Animal();
        animal.setNome(nome);
        animal.setClientId(clientId);
        animalService.remover(animal);
        return new RedirectView("/animais-listar?clientId=" + animal.getClientId());
    }
}