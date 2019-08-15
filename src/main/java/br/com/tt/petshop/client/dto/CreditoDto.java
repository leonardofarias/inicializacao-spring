package br.com.tt.petshop.client.dto;

public class CreditoDto {

    private String situacao;
    private int pontos;

    public CreditoDto() {
    }

    public CreditoDto(String situacao, int pontos) {
        this.situacao = situacao;
        this.pontos = pontos;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
