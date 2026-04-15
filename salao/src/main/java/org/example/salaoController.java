package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class salaoController {

    @FXML
    private void BtVoltar() {
        try {
            App.setRoot("salao");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void BtCabeleireiro() {
        try {
            App.setRoot("cabeleireiro");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void BtCliente() {
        try {
            App.setRoot("cliente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void BtCabelo() {
        try {
            App.setRoot("cabelo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private cabeleireiro cabeleireiro = new cabeleireiro();

    @FXML
    private cliente cliente = new cliente();

    @FXML
    private cabelo cabelo = new cabelo();


    @FXML
    private Button BtCabeleireiro;

    @FXML
    private Button BtCabelo;

    @FXML
    private Button BtCliente;

    @FXML
    private Button BtCortar;

    @FXML
    private Button BtPintar;

    @FXML
    private Button BtSecar;

    @FXML
    private Button BtVoltar;

    @FXML
    private Button BtEsperar;

    @FXML
    private Button BtPagar;

    @FXML
    private Button BtSentar;

    @FXML
    private Button BtAlisar;

    @FXML
    private Button BtCortarCabelo;

    @FXML
    private Button BtEnrolar;

    @FXML
    private Label texto;

    @FXML
    private void BtCortar() {
        texto.setText(cabeleireiro.cortar());
    }
    @FXML
    private void BtPintar() {
        texto.setText(cabeleireiro.pintar());
    }
    @FXML
    private void BtSecar() {
        texto.setText(cabeleireiro.secar());
    }
    @FXML
    private void BtSentar() {
        texto.setText(cliente.sentar());
    }
    @FXML
    private void BtEsperar() {
        texto.setText(cliente.esperar());
    }
    @FXML
    private void BtPagar() {
        texto.setText(cliente.pagar());
    }
    @FXML
    private void BtCortarCabelo() {
        texto.setText(cabelo.cortarCabelo());
    }
    @FXML
    private void BtAlisar() {
        texto.setText(cabelo.alisar());
    }
    @FXML
    private void BtEnrolar() {
        texto.setText(cabelo.enrolar());
    }


}
