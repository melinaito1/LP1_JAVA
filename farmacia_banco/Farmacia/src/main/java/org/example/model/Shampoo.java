package org.example.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Shampoo {

    private int id;
    private String marcaShampoo;
    private String tipoShampoo;
    private Double precoShampoo;

    private BooleanProperty selecionado = new SimpleBooleanProperty(false);

    public Shampoo() {
    }

    public Shampoo(String marcaShampoo, String tipoShampoo, Double precoShampoo) {
        this.marcaShampoo = marcaShampoo;
        this.tipoShampoo = tipoShampoo;
        this.precoShampoo = precoShampoo;
    }

    public Shampoo(int id, String marcaShampoo, String tipoShampoo, Double precoShampoo) {
        this.id = id;
        this.marcaShampoo = marcaShampoo;
        this.tipoShampoo = tipoShampoo;
        this.precoShampoo = precoShampoo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMarcaShampoo() {
        return marcaShampoo;
    }

    public void setMarcaShampoo(String marcaShampoo) {
        this.marcaShampoo = marcaShampoo;
    }


    public String getTipoShampoo() {
        return tipoShampoo;
    }

    public void setTipoShampoo(String tipoShampoo) {
        this.tipoShampoo = tipoShampoo;
    }


    public Double getPrecoShampoo() {
        return precoShampoo;
    }

    public void setPrecoShampoo(Double precoShampoo) {
        this.precoShampoo = precoShampoo;
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