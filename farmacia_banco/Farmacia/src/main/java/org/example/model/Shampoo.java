package org.example.model;

public class Shampoo {
    private String marcaShampoo = "Pantene";
    private String tipoShampoo = "Liso";
    private String tamanhoShampoo = "500ml";

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

    public String getTamanhoShampoo() {
        return tamanhoShampoo;
    }

    public void setTamanhoShampoo(String tamanhoShampoo) {
        this.tamanhoShampoo = tamanhoShampoo;
    }

    public String lavar(){
        return "O cliente lavou o cabelo com o shampoo";
    }

    public String enxaguar(){
        return "O cliente enxaguou o shampoo do cabelo";
    }

    public String espumar(){
        return "O shampoo faz espuma";
    }

}
