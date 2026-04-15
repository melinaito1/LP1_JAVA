package org.example;

public class cabeleireiro {
    private String nomeCabeleireiro = "Carmem";
    private String turnoCabeleireiro = "tarde";
    private Integer idadeCabeleireiro = 28;

    private String getNomeCabeleireiro() {
        return nomeCabeleireiro;
    }
    public void setNomeCabeleireiro(String nomeCabeleireiro) {
        this.nomeCabeleireiro = nomeCabeleireiro;
    }
    private String getTurnoCabeleireiro() {
        return turnoCabeleireiro;
    }
    public void setTurnoCabeleireiro(String turnoCabeleireiro) {
        this.turnoCabeleireiro = turnoCabeleireiro;
    }
    private Integer getIdadeCabeleireiro() {
        return idadeCabeleireiro;
    }
    public void setIdadeCabeleireiro(Integer idadeCabeleireiro) {
        this.idadeCabeleireiro = idadeCabeleireiro;
    }

    public String cortar(){
        return "Carmem cortou o cabelo.";
    }

    public String pintar(){
        return "Carmem pintou o cabelo.";
    }

    public String secar(){
        return "Carmem secou o cabelo da cliente.";
    }

}
