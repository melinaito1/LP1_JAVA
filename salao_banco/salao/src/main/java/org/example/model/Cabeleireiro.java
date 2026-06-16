package org.example.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Cabeleireiro {

    private int id;
    private String nome;
    private String turno;
    private String especialidade;

    private BooleanProperty selecionado = new SimpleBooleanProperty(false);

    public Cabeleireiro() {
    }

    public Cabeleireiro(String nome, String turno, String especialidade) {
        this.nome = nome;
        this.turno = turno;
        this.especialidade = especialidade;
    }

    public Cabeleireiro(int id, String nome, String turno, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.turno = turno;
        this.especialidade = especialidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }


    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
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