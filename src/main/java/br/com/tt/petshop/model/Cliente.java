package br.com.tt.petshop.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import br.com.tt.petshop.model.vo.Cpf;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME_CLIENTE")
    private String nome;

    @Embedded
    private Cpf cpf;

    @Column(name = "INADIMPLENTE")
    private Boolean inadimplente;

    @OneToMany(mappedBy = "cliente")
    private List<Animal> animais;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDADE")
    private Unidade unidade;

    public Cliente(){
        this.inadimplente = Boolean.FALSE;
    }

    public Cliente(Long id) {
        this.id = id;
    }

    public Cliente(Long id, String nome, String cpf, Boolean inadimplente) {
        this.id = id;
        this.nome = nome;
        this.cpf = new Cpf(cpf);
        this.inadimplente = inadimplente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    public Boolean getInadimplente() {
        return Objects.nonNull(inadimplente) && inadimplente;
    }

    public void setInadimplente(Boolean inadimplente) {
        this.inadimplente = inadimplente;
    }

    public List<Animal> getAnimais() {
        return animais;
    }

    public void setAnimais(List<Animal> animais) {
        this.animais = animais;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
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
