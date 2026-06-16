package org.example.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Remedio {

    private int id;
    private String nomeRemedio;
    private String tipoRemedio;
    private Double precoRemedio;

    private BooleanProperty selecionado = new SimpleBooleanProperty(false);

    public Remedio() {
    }

    public Remedio(String nomeRemedio, String tipoRemedio, Double precoRemedio) {
        this.nomeRemedio = nomeRemedio;
        this.tipoRemedio = tipoRemedio;
        this.precoRemedio = precoRemedio;
    }

    public Remedio(int id, String nomeRemedio, String tipoRemedio, Double precoRemedio) {
        this.id = id;
        this.nomeRemedio = nomeRemedio;
        this.tipoRemedio = tipoRemedio;
        this.precoRemedio = precoRemedio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


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