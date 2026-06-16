package org.example.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Barraca {

    private int id;
    private String tamanho;
    private String cor;
    private Double altura;

    private BooleanProperty selecionado = new SimpleBooleanProperty(false);

    public Barraca() {
    }

    public Barraca(String tamanho, String cor, Double altura) {
        this.tamanho = tamanho;
        this.cor = cor;
        this.altura = altura;
    }

    public Barraca(int id, String tamanho, String cor, Double altura) {
        this.id = id;
        this.tamanho = tamanho;
        this.cor = cor;
        this.altura = altura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }


    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }


    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }


    public boolean isSelecionado() {
        return selecionado.get();
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado.set(selecionado);
    }

    public BooleanProperty selecionadoProperty() {
        return selecionado;
    }
}