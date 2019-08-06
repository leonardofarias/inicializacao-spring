package br.com.tt.petshop.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    private BigDecimal valor;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "ID_ANIMAL")
    private Animal animal;

    public Produto() {
    }

    public Produto(BigDecimal valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "valor=" + valor +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
