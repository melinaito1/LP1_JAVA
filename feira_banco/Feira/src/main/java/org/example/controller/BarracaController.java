package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.BarracaDAO;
import org.example.model.Barraca;

import java.io.IOException;
import java.util.List;

public class BarracaController {

    @FXML
    private TextField txtTamanho;

    @FXML
    private TextField txtCor;

    @FXML
    private TextField txtAltura;

    @FXML
    private TableView<Barraca> tabelaBarraca;

    @FXML
    private TableColumn<Barraca, Boolean> colSelecionado;

    @FXML
    private TableColumn<Barraca, Integer> colId;

    @FXML
    private TableColumn<Barraca, String> colTamanho;

    @FXML
    private TableColumn<Barraca, String> colCor;

    @FXML
    private TableColumn<Barraca, Double> colAltura;

    private BarracaDAO barracaDAO = new BarracaDAO();

    private ObservableList<Barraca> listaBarracas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarBarracas();

        tabelaBarraca.setOnMouseClicked(event -> {
            Barraca barraca = tabelaBarraca.getSelectionModel().getSelectedItem();

            if (barraca != null) {
                preencherCampos(barraca);
            }
        });
    }

    private void configurarTabela() {
        tabelaBarraca.setEditable(true);

        colSelecionado.setCellValueFactory(cellData -> {
            Barraca barracaAtual = cellData.getValue();

            barracaAtual.selecionadoProperty().addListener((obs, estavaSelecionado, estaSelecionado) -> {
                if (estaSelecionado) {
                    for (Barraca barraca : listaBarracas) {
                        if (barraca != barracaAtual) {
                            barraca.setSelecionado(false);
                        }
                    }

                    preencherCampos(barracaAtual);
                    tabelaBarraca.refresh();
                }
            });

            return barracaAtual.selecionadoProperty();
        });

        colSelecionado.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionado));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTamanho.setCellValueFactory(new PropertyValueFactory<>("tamanho"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        colAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));

        tabelaBarraca.setItems(listaBarracas);
    }

    @FXML
    void cadastrarBarraca(ActionEvent event) {
        String tamanho = txtTamanho.getText();
        String cor = txtCor.getText();
        String alturaTexto = txtAltura.getText();

        if (tamanho.isEmpty() || cor.isEmpty() || alturaTexto.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        Double altura;

        try {
            altura = converterAltura(alturaTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Digite uma altura válida. Exemplo: 2.50");
            return;
        }

        Barraca barraca = new Barraca(tamanho, cor, altura);

        barracaDAO.cadastrar(barraca);

        mostrarAlerta("Sucesso", "Barraca cadastrada com sucesso.");

        limparCampos();
        carregarBarracas();
    }

    @FXML
    void buscarBarraca(ActionEvent event) {
        String tamanhoBusca = txtTamanho.getText();

        if (tamanhoBusca.isEmpty()) {
            carregarBarracas();
            return;
        }

        List<Barraca> resultado = barracaDAO.buscarPorTamanho(tamanhoBusca);

        listaBarracas.clear();
        listaBarracas.addAll(resultado);
    }

    @FXML
    void atualizarBarraca(ActionEvent event) {
        Barraca barracaSelecionada = getBarracaMarcada();

        if (barracaSelecionada == null) {
            mostrarAlerta("Erro", "Selecione uma barraca pelo checkbox para atualizar.");
            return;
        }

        String tamanho = txtTamanho.getText();
        String cor = txtCor.getText();
        String alturaTexto = txtAltura.getText();

        if (tamanho.isEmpty() || cor.isEmpty() || alturaTexto.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        Double altura;

        try {
            altura = converterAltura(alturaTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Digite uma altura válida. Exemplo: 2.50");
            return;
        }

        barracaSelecionada.setTamanho(tamanho);
        barracaSelecionada.setCor(cor);
        barracaSelecionada.setAltura(altura);

        barracaDAO.atualizar(barracaSelecionada);

        mostrarAlerta("Sucesso", "Barraca atualizada com sucesso.");

        limparCampos();
        carregarBarracas();
    }

    @FXML
    void excluirBarraca(ActionEvent event) {
        Barraca barracaSelecionada = getBarracaMarcada();

        if (barracaSelecionada == null) {
            mostrarAlerta("Erro", "Selecione uma barraca pelo checkbox para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir esta barraca?");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                barracaDAO.deletar(barracaSelecionada.getId());

                mostrarAlerta("Sucesso", "Barraca excluída com sucesso.");

                limparCampos();
                carregarBarracas();
            }
        });
    }

    @FXML
    void BtVoltar(ActionEvent event) {
        try {
            App.setRoot("Feira");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarBarracas() {
        List<Barraca> barracas = barracaDAO.listarTodos();

        listaBarracas.clear();
        listaBarracas.addAll(barracas);
    }

    private Barraca getBarracaMarcada() {
        for (Barraca barraca : listaBarracas) {
            if (barraca.isSelecionado()) {
                return barraca;
            }
        }

        return null;
    }

    private void preencherCampos(Barraca barraca) {
        txtTamanho.setText(barraca.getTamanho());
        txtCor.setText(barraca.getCor());
        txtAltura.setText(String.valueOf(barraca.getAltura()));
    }

    private void limparCampos() {
        txtTamanho.clear();
        txtCor.clear();
        txtAltura.clear();

        for (Barraca barraca : listaBarracas) {
            barraca.setSelecionado(false);
        }

        tabelaBarraca.refresh();
    }

    private Double converterAltura(String alturaTexto) {
        alturaTexto = alturaTexto.replace(",", ".");
        return Double.parseDouble(alturaTexto);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}