# LP1_JAVA

Atividades da matéria de Lógica de Programação 1

// UML: FARMACIA
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
    b.encher();

    Pessoa p = new Pessoa("Feminino");
    p.falar();
    p.comprar();

    Remedio r = new Remedio();
    r.curar();
    r.acalmar();
    }

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
    public void encher(){
        System.out.println("O balão está cheio.");
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
    public Pessoa(double altura){
        this.altura = altura;
    }

    public void comprar(){
        System.out.println("Cliente fez uma compra.");
    }

    public void falar(){
        System.out.println("O cliente falou que quer comprar.");
    }

}

CLASSE REMEDIO
public class Remedio {

    private String composicao;
    private String validade;
    private double valor;

    public void setComposicao(String composicao) {
        this.composicao = composicao;
    }
    public void setValidade(String validade) {
        this.validade = validade;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public void curar() {
        System.out.println("O remédio cura.");
    }

    public void acalmar() {
        System.out.println("O remédio acalma.");
    }

}


