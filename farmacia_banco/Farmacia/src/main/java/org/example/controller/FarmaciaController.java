package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.App;

import java.io.IOException;

public class FarmaciaController {

    @FXML
    void BtFuncionario(ActionEvent event) {
        try {
            App.setRoot("Funcionario");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BtRemedio(ActionEvent event) {
        try {
            App.setRoot("Remedio");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BtShampoo(ActionEvent event) {
        try {
            App.setRoot("Shampoo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}