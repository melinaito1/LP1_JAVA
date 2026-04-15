package org.example;

public class Funcionario {
    private String nomeFuncionario = "Patrick";
    private String funcaoCargo = "Atendente";
    private String idFuncionario = "F1234";

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getFuncaoCargo() {
        return funcaoCargo;
    }

    public void setFuncaoCargo(String funcaoCargo) {
        this.funcaoCargo = funcaoCargo;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String vender(){
        return "Patrick vendeu dipirona 1g";
    }

    public String falar(){
        return "Olá, me chamo " + nomeFuncionario + ". Em que posso ajudar?";
    }

    public String repor(){
        return "O funcionário fez a reposição";
    }

}
