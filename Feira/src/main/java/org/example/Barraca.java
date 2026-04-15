package org.example;

public class Barraca {
    private String tamanhoBarraca = "Grande";
    private String corBarraca = "Vermelha";
    private Double alturaBarraca = 2.0;

    private String getTamanhoBarraca(){
        return tamanhoBarraca;
    }
    public void setTamanhoBarraca(String tamanhoBarraca) {
        this.tamanhoBarraca = tamanhoBarraca;
    }

    private String getCorBarraca(){
        return corBarraca;
    }
    public void setCorBarraca(String corBarraca) {
        this.corBarraca = corBarraca;
    }

    private Double getAlturaBarraca() {
        return alturaBarraca;
    }
    public void setAlturaBarraca() {
        this.alturaBarraca = alturaBarraca;
    }

    public String montar() {
        return "A barraca foi montada";
    }
    public String desmontar() {
        return "A barraca foi desmontada";
    }
    public String limpar() {
        return "A barraca foi limpa";
    }
}
