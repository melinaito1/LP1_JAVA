package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class FarmaciaController {



    @FXML
    private Button BtVoltar;

    private Button BtFuncionario;

    @FXML
    private Button BtRemedio;

    @FXML
    private Button BtShampoo;

    @FXML
    private AnchorPane TableFarmacia;

    @FXML
    private Label texto;

    @FXML
    private Button BtComprar;

    @FXML
    private Button BtCurar;

    @FXML
    private Button BtDosar;

    @FXML
    private Button BtEnxaguar;

    @FXML
    private Button BtEspumar;

    @FXML
    private Button BtLavar;


    @FXML
    private void BtVoltar() {
        try {
            App.setRoot("Farmacia");
        } catch (Exception e) {
            e.printStackTrace();
        }}
    @FXML
    void BtFuncionario() {
        try {
            App.setRoot("Funcionario");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void BtRemedio() {
        try {
            App.setRoot("Remedio");
            } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void BtShampoo() {
        try {
            App.setRoot("Shampoo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        @FXML
        private Funcionario funcionario = new Funcionario();

        @FXML
        private Remedio remedio = new Remedio();

        @FXML
        private Shampoo shampoo = new Shampoo();


    @FXML
    private void BtVender() {
        texto.setText(funcionario.vender());
    }

    @FXML
    private void BtFalar() {
        texto.setText(funcionario.falar());
    }

    @FXML
    private void BtRepor() {
        texto.setText(funcionario.repor());
    }

    @FXML
    private void BtComprar() {
        texto.setText(remedio.comprar());
    }

    @FXML
    private void BtCurar() {
        texto.setText(remedio.curar());
    }

    @FXML
    private void BtDosar() {
        texto.setText(remedio.dosar());
    }

    @FXML
    private void BtLavar() {
        texto.setText(shampoo.lavar());
    }

    @FXML
    private void BtEnxaguar() {
        texto.setText(shampoo.enxaguar());
    }

    @FXML
    private void BtEspumar() {
        texto.setText(shampoo.espumar());
    }


}
