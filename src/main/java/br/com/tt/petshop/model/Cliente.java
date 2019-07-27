package br.com.tt.petshop.model;

import java.util.Objects;

public class Cliente {

    private Long id;
    private String nome;
    private String cpf;
    private Boolean inadimplente;

    public Cliente(){
        this.inadimplente = Boolean.FALSE;
    }

    public Cliente(Long id, String nome, String cpf, Boolean inadimplente) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.inadimplente = inadimplente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getInadimplente() {
        return Objects.nonNull(inadimplente) && inadimplente;
    }

    public void setInadimplente(Boolean inadimplente) {
        this.inadimplente = inadimplente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
