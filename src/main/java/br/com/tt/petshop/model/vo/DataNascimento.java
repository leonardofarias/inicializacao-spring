package br.com.tt.petshop.model.vo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class DataNascimento {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "data_nascimento")
    private LocalDate data;

    public DataNascimento() {
    }

    public DataNascimento(LocalDate data) {
        this.data = data;
    }

    public boolean isValid(){
        return LocalDate.now().isBefore(data);
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataNascimento{" +
                "data=" + data +
                '}';
    }
}
