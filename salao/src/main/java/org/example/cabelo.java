package org.example;

public class cabelo {
    private String tipoCabelo = "Liso";
    private String corCabelo = "preto";
    private String comprimento = "médio";

    private String getTipoCabelo() {
        return tipoCabelo;
    }

    public void setTipoCabelo(String tipoCabelo) {
        this.tipoCabelo = tipoCabelo;
    }

    private String getCorCabelo() {
        return corCabelo;
    }

    public void setCorCabelo(String corCabelo) {
        this.corCabelo = corCabelo;
    }

    private String getComprimento() {
        return comprimento;
    }

    public void setComprimento(String comprimento) {
        this.comprimento = comprimento;
    }

    public String cortarCabelo() {
        return "O cabelo foi cortado";
    }
    public String alisar() {
        return "O cabelo foi alisado";
    }
    public String enrolar() {
        return "O cabelo foi enrolado";
    }
}
