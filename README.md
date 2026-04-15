# LP1_JAVA

Atividades da matéria de Lógica de Programação 1

UML: FARMACIA <br>
Classe Funcionario <br>
Atributos: nomeFuncionario, funcaoCargo, idFuncionario. <br> 
Métodos: vender(), falar(), repor().<br> <br>
Classe Remedio <br>
Atributos: nomeRemedio, tipoRemedio, precoRemedio. <br>
Métodos: comprar(), curar(), dosar(). <br><br>
Classe Shampoo <br>
Atributos: marcaShampoo, tipoShampoo, tamanhoShampoo. <br>
Métodos: lavar(), enxaguar(), espumar(). <br><br>

MAIN
void main() {

    Funcionario f = new Funcionario("Patrick");
    f.vender();
    f.repor();

    Shampoo s = new Shampoo("Grande");
    s.espumar();
    s.enxaguar();

    Remedio r = new Remedio();
    r.curar();
    r.comprar();
    }

CLASSE FUNCIONARIO
public class Funcionario {

    private String nomeFuncionario;
    private String funcaoCargo;
    private String idFuncionario;

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


