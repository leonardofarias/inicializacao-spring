package br.com.tt.petshop.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TB_UNIDADE")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "endereco")
    private String endereco;

    public Unidade() {
    }

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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
