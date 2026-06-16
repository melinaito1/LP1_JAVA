package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.App;

import java.io.IOException;

public class SalaoController {

    @FXML
    void BtCabeleireiro(ActionEvent event) {
        try {
            App.setRoot("Cabeleireiro");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BtCabelo(ActionEvent event) {
        try {
            App.setRoot("Cabelo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BtCliente(ActionEvent event) {
        try {
            App.setRoot("Cliente");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}