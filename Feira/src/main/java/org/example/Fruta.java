package org.example;

import javafx.scene.image.ImageView;

public class Fruta {
    private String nome = "Uva";
    private String cor = "verde";
    private Double preco;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCor() {
        return cor;
    }
    public void setCor(String genero) {
        this.cor = cor;
    }
    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }
    public String comer(){
        return "O cliente comeu a uva.";
    }
    public String descascar(){
        return "A manga foi descascada.";
    }
    public String comprar(){
        return "O cliente comprou uva.";
    }
}
