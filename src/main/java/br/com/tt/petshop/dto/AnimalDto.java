package br.com.tt.petshop.dto;

import br.com.tt.petshop.api.groups.OnPost;
import br.com.tt.petshop.enums.EspecieEnum;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class AnimalDto {

    @Null(groups = OnPost.class)
    private Long id;

    @NotBlank(message = "Informe o nome do animal")
    @Size(min = 2, max = 100, message = "O nome deve conter de {min} a {max}")
    private String nome;

    @PastOrPresent
    private LocalDate dataNascimento;

    @NotNull
    private EspecieEnum especie;

    @NotNull
    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public EspecieEnum getEspecie() {
        return especie;
    }

    public void setEspecie(EspecieEnum especie) {
        this.especie = especie;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}