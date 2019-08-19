package br.com.tt.petshop.model;

import br.com.tt.petshop.enums.EspecieEnum;
import br.com.tt.petshop.model.vo.DataNascimento;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_ANIMAL")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long id;

    @NotBlank
    private String nome;

    @Embedded
    private DataNascimento dataNascimento;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EspecieEnum especie;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDADE")
    private Unidade unidade;

    @OneToMany(mappedBy = "animal")
    @JsonIgnore
    private List<Produto> produtos;

    public Animal(){
        this.dataNascimento = new DataNascimento();
    }

    public Animal(String nome, LocalDate data, EspecieEnum especie, Long clienteId) {
        this.nome = nome;
        this.dataNascimento = new DataNascimento(data);
        this.especie = especie;
        this.cliente = new Cliente(clienteId, null, null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public DataNascimento getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(DataNascimento dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public EspecieEnum getEspecie() {
        return especie;
    }

    public void setEspecie(EspecieEnum especie) {
        this.especie = especie;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}