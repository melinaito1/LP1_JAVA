package org.example.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Cabelo {

    private int id;
    private String tipo;
    private String cor;
    private String comprimento;

    private BooleanProperty selecionado = new SimpleBooleanProperty(false);

    public Cabelo() {
    }

    public Cabelo(String tipo, String cor, String comprimento) {
        this.tipo = tipo;
        this.cor = cor;
        this.comprimento = comprimento;
    }

    public Cabelo(int id, String tipo, String cor, String comprimento) {
        this.id = id;
        this.tipo = tipo;
        this.cor = cor;
        this.comprimento = comprimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }


    public String getComprimento() {
        return comprimento;
    }

    public void setComprimento(String comprimento) {
        this.comprimento = comprimento;
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