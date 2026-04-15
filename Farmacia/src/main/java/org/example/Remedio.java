package org.example;

public class Remedio {
    private String nomeRemedio = "Dipirona";
    private String tipoRemedio = "Analgésico";
    private Double precoRemedio = 11.50;

    public String getNomeRemedio() {
        return nomeRemedio;
    }

    public void setNomeRemedio(String nomeRemedio) {
        this.nomeRemedio = nomeRemedio;
    }

    public String getTipoRemedio() {
        return tipoRemedio;
    }

    public void setTipoRemedio(String tipoRemedio) {
        this.tipoRemedio = tipoRemedio;
    }

    public Double getPrecoRemedio() {
        return precoRemedio;
    }

    public void setPrecoRemedio(Double precoRemedio) {
        this.precoRemedio = precoRemedio;
    }

    public String comprar(){
        return "O cliente comprou dipirona";
    }

    public String curar(){
        return "Dipirona fez efeito em 10 minutos";
    }

    public String dosar(){
        return "A dosagem da dipirona é 1g";
    }

}
