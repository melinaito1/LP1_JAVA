package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class feiraController {
    @FXML
    private void BtVoltar() {
        try {
            App.setRoot("feira");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void BtFruta() {
        try {
            App.setRoot("fruta");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void BtFeirante() {
        try {
            App.setRoot("feirante");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void BtBarraca() {
        try {
            App.setRoot("barraca");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Fruta fruta = new Fruta();

    @FXML
    private Feirante feirante = new Feirante();

    @FXML
    private Barraca barraca = new Barraca();

    @FXML
    private Button BtBarraca;

    @FXML
    private Button BtFeirante;

    @FXML
    private Button BtFruta;

    @FXML
    private Button BtComer;

    @FXML
    private Button BtComprar;

    @FXML
    private Button BtDescascar;

    @FXML
    private Button BtVoltar;

    @FXML
    private Label texto;

    @FXML
    private Button BtLavar;

    @FXML
    private Button BtOrganizar;

    @FXML
    private Button BtVender;

    @FXML
    private Button BtDesmontar;

    @FXML
    private Button BtLimpar;

    @FXML
    private Button BtMontar;


    @FXML
    private void BtComer() {
        texto.setText(fruta.comer());
    }

    @FXML
    private void BtDescascar() {
        texto.setText(fruta.descascar());
    }

    @FXML
    private void BtComprar() {
        texto.setText(fruta.comprar());
    }

    @FXML
    private void BtVender() {
        texto.setText(feirante.vender());
    }

    @FXML
    private void BtOrganizar() {
        texto.setText(feirante.organizar());
    }

    @FXML
    private void BtLavar() {
        texto.setText(feirante.lavar());
    }

    @FXML
    private void BtMontar() {
        texto.setText(barraca.montar());
    }

    @FXML
    private void BtLimpar() {
        texto.setText(barraca.limpar());
    }

    @FXML
    private void BtDesmontar() {
        texto.setText(barraca.desmontar());
    }
}
