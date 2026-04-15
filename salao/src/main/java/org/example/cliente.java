package org.example;

public class cliente {
    private String nomeCliente = "Célia";
    private Integer idadeCliente = 49;
    private String horaCliente = "15h30";

    private String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    private Integer getIdadeCliente() {
        return idadeCliente;
    }

    public void setIdadeCliente(Integer idadeCliente) {
        this.idadeCliente = idadeCliente;
    }

    private String getHoraCliente() {
        return horaCliente;
    }

    public void setHoraCliente(String horaCliente) {
        this.horaCliente = horaCliente;
    }

    public String sentar() {
        return "Célia chegou ao salão e sentou na cadeira";
    }
    public String esperar() {
        return "Célia esperou por 1h até a finalização do corte";
    }
    public String pagar() {
        return "Célia pagou R$100 pelo corte";
    }

}
