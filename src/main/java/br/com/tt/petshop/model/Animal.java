package br.com.tt.petshop.model;

import br.com.tt.petshop.enums.EspecieEnum;
import br.com.tt.petshop.model.vo.DataNascimento;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "TB_ANIMAL")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long idAnimal;
    private String nome;

    @Embedded
    private DataNascimento dataNascimento;
    @Enumerated(EnumType.STRING)
    private EspecieEnum especie;
    private Long clientId;

    public Animal(){
        this.dataNascimento = new DataNascimento();
    }

    public Animal(Long idAnimal, String nome, LocalDate data, EspecieEnum especie, Long clientId) {
        this.idAnimal = idAnimal;
        this.nome = nome;
        this.dataNascimento = new DataNascimento(data);
        this.especie = especie;
        this.clientId = clientId;
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Long idAnimal) {
        this.idAnimal = idAnimal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(idAnimal, animal.idAnimal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAnimal);
    }
}