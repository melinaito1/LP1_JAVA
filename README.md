# LP1_JAVA

Atividades da matéria de Lógica de Programação 1

// UML:
// Classe Balão
//    Atributos: cor, material, volume. Métodos: estourar(), encher(), bater().
// Classe Pessoa
//    Atributos: gênero, altura, peso. Métodos: andar(), falar(), comprar().
// Classe Remédio
//    Atributos: composição, validade, valor. Métodos: curar(), desinflamar(), acalmar()

MAIN
void main() {

    Balao b = new Balao("Verde");
    b.estourar();

    Pessoa p = new Pessoa("Feminino");
    p.comprar();

CLASSE BALAO
public class Balao {

    private String cor;
    private String material;
    private double volume;

    public void setCor(String cor){
        this.cor = cor;
}
    public void setMaterial(String material){
        this.material = material;
    }
    public void setVolume(double volume){
        this.volume = volume;
    }

    public Balao(String cor){
        this.cor = cor;
    }

    public void estourar(){
        this.volume = 1000;
        IO.println("O balão estourou! " + "O volume é igual a " + volume);
    }
}


CLASSE PESSOA
import java.sql.SQLOutput;

public class Pessoa {

    private String genero;
    private double altura;
    private double peso;

    public void setGenero(String genero){
        this.genero = genero;
    }
    public void setAltura(double altura){
        this.altura = altura;
    }
    public void setPeso(double peso){
        this.peso = peso;
    }

    public Pessoa(String genero){
        this.genero = genero;
    }
    public void comprar(){
        System.out.println("Cliente fez uma compra.");
    }

}

