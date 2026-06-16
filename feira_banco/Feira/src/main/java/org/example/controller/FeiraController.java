package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.App;

import java.io.IOException;

public class FeiraController {

    @FXML
    void BtFeirante(ActionEvent event) {
        try {
            App.setRoot("Feirante");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BtFruta(ActionEvent event) {
        try {
            App.setRoot("Fruta");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BtBarraca(ActionEvent event) {
        try {
            App.setRoot("Barraca");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}