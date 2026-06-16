package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.CabeloDAO;
import org.example.model.Cabelo;

import java.io.IOException;
import java.util.List;

public class CabeloController {

    @FXML
    private TextField txtTipo;

    @FXML
    private TextField txtCor;

    @FXML
    private TextField txtComprimento;

    @FXML
    private TableView<Cabelo> tabelaCabelo;

    @FXML
    private TableColumn<Cabelo, Boolean> colSelecionado;

    @FXML
    private TableColumn<Cabelo, Integer> colId;

    @FXML
    private TableColumn<Cabelo, String> colTipo;

    @FXML
    private TableColumn<Cabelo, String> colCor;

    @FXML
    private TableColumn<Cabelo, String> colComprimento;

    private CabeloDAO cabeloDAO = new CabeloDAO();

    private ObservableList<Cabelo> listaCabelos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarCabelos();

        tabelaCabelo.setOnMouseClicked(event -> {
            Cabelo cabelo = tabelaCabelo.getSelectionModel().getSelectedItem();

            if (cabelo != null) {
                preencherCampos(cabelo);
            }
        });
    }

    private void configurarTabela() {
        tabelaCabelo.setEditable(true);

        colSelecionado.setCellValueFactory(cellData -> {
            Cabelo cabeloAtual = cellData.getValue();

            cabeloAtual.selecionadoProperty().addListener((obs, estavaSelecionado, estaSelecionado) -> {
                if (estaSelecionado) {
                    for (Cabelo cabelo : listaCabelos) {
                        if (cabelo != cabeloAtual) {
                            cabelo.setSelecionado(false);
                        }
                    }

                    preencherCampos(cabeloAtual);
                    tabelaCabelo.refresh();
                }
            });

            return cabeloAtual.selecionadoProperty();
        });

        colSelecionado.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionado));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        colComprimento.setCellValueFactory(new PropertyValueFactory<>("comprimento"));

        tabelaCabelo.setItems(listaCabelos);
    }

    @FXML
    void cadastrarCabelo(ActionEvent event) {
        String tipo = txtTipo.getText();
        String cor = txtCor.getText();
        String comprimento = txtComprimento.getText();

        if (tipo.isEmpty() || cor.isEmpty() || comprimento.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        Cabelo cabelo = new Cabelo(tipo, cor, comprimento);

        cabeloDAO.cadastrar(cabelo);

        mostrarAlerta("Sucesso", "Cabelo cadastrado com sucesso.");

        limparCampos();
        carregarCabelos();
    }

    @FXML
    void buscarCabelo(ActionEvent event) {
        String tipoBusca = txtTipo.getText();

        if (tipoBusca.isEmpty()) {
            carregarCabelos();
            return;
        }

        List<Cabelo> resultado = cabeloDAO.buscarPorTipo(tipoBusca);

        listaCabelos.clear();
        listaCabelos.addAll(resultado);
    }

    @FXML
    void atualizarCabelo(ActionEvent event) {
        Cabelo cabeloSelecionado = getCabeloMarcado();

        if (cabeloSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um cabelo pelo checkbox para atualizar.");
            return;
        }

        String tipo = txtTipo.getText();
        String cor = txtCor.getText();
        String comprimento = txtComprimento.getText();

        if (tipo.isEmpty() || cor.isEmpty() || comprimento.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        cabeloSelecionado.setTipo(tipo);
        cabeloSelecionado.setCor(cor);
        cabeloSelecionado.setComprimento(comprimento);

        cabeloDAO.atualizar(cabeloSelecionado);

        mostrarAlerta("Sucesso", "Cabelo atualizado com sucesso.");

        limparCampos();
        carregarCabelos();
    }

    @FXML
    void excluirCabelo(ActionEvent event) {
        Cabelo cabeloSelecionado = getCabeloMarcado();

        if (cabeloSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um cabelo pelo checkbox para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir este cabelo?");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                cabeloDAO.deletar(cabeloSelecionado.getId());

                mostrarAlerta("Sucesso", "Cabelo excluído com sucesso.");

                limparCampos();
                carregarCabelos();
            }
        });
    }

    @FXML
    void BtVoltar(ActionEvent event) {
        try {
            App.setRoot("Salao");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarCabelos() {
        List<Cabelo> cabelos = cabeloDAO.listarTodos();

        listaCabelos.clear();
        listaCabelos.addAll(cabelos);
    }

    private Cabelo getCabeloMarcado() {
        for (Cabelo cabelo : listaCabelos) {
            if (cabelo.isSelecionado()) {
                return cabelo;
            }
        }

        return null;
    }

    private void preencherCampos(Cabelo cabelo) {
        txtTipo.setText(cabelo.getTipo());
        txtCor.setText(cabelo.getCor());
        txtComprimento.setText(cabelo.getComprimento());
    }

    private void limparCampos() {
        txtTipo.clear();
        txtCor.clear();
        txtComprimento.clear();

        for (Cabelo cabelo : listaCabelos) {
            cabelo.setSelecionado(false);
        }

        tabelaCabelo.refresh();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}