package model;

public class Shampoo {

    private int id;
    private String marca;
    private String tipo;
    private String tamanho;

    public Shampoo() {}

    public Shampoo(String marca, String tipo, String tamanho) {
        this.marca = marca;
        this.tipo = tipo;
        this.tamanho = tamanho;
    }

    public Shampoo(int id, String marca, String tipo, String tamanho) {
        this.id = id;
        this.marca = marca;
        this.tipo = tipo;
        this.tamanho = tamanho;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTamanho() { return tamanho; }
    public void setTamanho(String tamanho) { this.tamanho = tamanho; }

    @Override
    public String toString() {
        return "Shampoo{id=" + id + ", marca='" + marca + "', tipo='" + tipo + "', tamanho='" + tamanho + "'}";
    }
}
