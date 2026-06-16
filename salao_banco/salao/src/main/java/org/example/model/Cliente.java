package org.example.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Cliente {

    private int id;
    private String nome;
    private String hora;
    private String servico;

    private BooleanProperty selecionado = new SimpleBooleanProperty(false);

    public Cliente() {
    }

    public Cliente(String nome, String hora, String servico) {
        this.nome = nome;
        this.hora = hora;
        this.servico = servico;
    }

    public Cliente(int id, String nome, String hora, String servico) {
        this.id = id;
        this.nome = nome;
        this.hora = hora;
        this.servico = servico;
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


    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }


    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
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