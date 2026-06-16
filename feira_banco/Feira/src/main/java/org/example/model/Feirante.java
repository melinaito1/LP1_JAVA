package org.example.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Feirante {

    private int id;
    private String nomeFeirante;
    private String localFeirante;
    private String cpfFeirante;

    private BooleanProperty selecionado = new SimpleBooleanProperty(false);

    public Feirante() {
    }

    public Feirante(String nomeFeirante, String localFeirante, String cpfFeirante) {
        this.nomeFeirante = nomeFeirante;
        this.localFeirante = localFeirante;
        this.cpfFeirante = cpfFeirante;
    }

    public Feirante(int id, String nomeFeirante, String localFeirante, String cpfFeirante) {
        this.id = id;
        this.nomeFeirante = nomeFeirante;
        this.localFeirante = localFeirante;
        this.cpfFeirante = cpfFeirante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNomeFeirante() {
        return nomeFeirante;
    }

    public void setNomeFeirante(String nomeFeirante) {
        this.nomeFeirante = nomeFeirante;
    }


    public String getLocalFeirante() {
        return localFeirante;
    }

    public void setLocalFeirante(String localFeirante) {
        this.localFeirante = localFeirante;
    }


    public String getCpfFeirante() {
        return cpfFeirante;
    }

    public void setCpfFeirante(String cpfFeirante) {
        this.cpfFeirante = cpfFeirante;
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