package org.example;

public class Feirante {
    private String nomeFeirante = "Marisa";
    private Double alturaFeirante = 1.60;
    private Integer idadeFeirante = 31;

    public String getNomeFeirante(){
        return nomeFeirante;
    }
    public void setNomeFeirante(String nomeFeirante){
        this.nomeFeirante = nomeFeirante;
    }
    public Double getAlturaFeirante(){
        return alturaFeirante;
    }
    public void setAlturaFeirante(Double alturaFeirante) {
        this.alturaFeirante = alturaFeirante;
    }

    public Integer getIdadeFeirante(){
        return idadeFeirante;
    }

    public void setIdadeFeirante(Integer idadeFeirante) {
        this.idadeFeirante = idadeFeirante;
    }

    public String vender(){
        return "Marisa vendeu 1kg de uva";
    }
    public String organizar(){
        return "Marisa organizou as frutas na barraca";
    }
    public String lavar(){
        return "Marisa lavou as frutas";
    }


}

