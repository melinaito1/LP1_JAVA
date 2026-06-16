package model;

public class Funcionario {

    private int id;
    private String nome;
    private String cargo;
    private String cpf;

    public Funcionario() {}

    public Funcionario(String nome, String cargo, String cpf) {
        this.nome = nome;
        this.cargo = cargo;
        this.cpf = cpf;
    }

    public Funcionario(int id, String nome, String cargo, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.cpf = cpf;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    @Override
    public String toString() {
        return "Funcionario{id=" + id + ", nome='" + nome + "', cargo='" + cargo + "', cpf='" + cpf + "'}";
    }
}
