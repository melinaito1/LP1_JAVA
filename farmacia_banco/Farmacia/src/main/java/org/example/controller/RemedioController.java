package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.RemedioDAO;
import org.example.model.Remedio;

import java.io.IOException;
import java.util.List;

public class RemedioController {

    @FXML
    private TextField txtNomeRemedio;

    @FXML
    private TextField txtTipoRemedio;

    @FXML
    private TextField txtPreco;

    @FXML
    private TableView<Remedio> tabelaRemedio;

    @FXML
    private TableColumn<Remedio, Boolean> colSelecionado;

    @FXML
    private TableColumn<Remedio, Integer> colId;

    @FXML
    private TableColumn<Remedio, String> colNomeRemedio;

    @FXML
    private TableColumn<Remedio, String> colTipo;

    @FXML
    private TableColumn<Remedio, Double> colPreco;

    private RemedioDAO remedioDAO = new RemedioDAO();

    private ObservableList<Remedio> listaRemedios = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarRemedios();

        tabelaRemedio.setOnMouseClicked(event -> {
            Remedio remedio = tabelaRemedio.getSelectionModel().getSelectedItem();

            if (remedio != null) {
                txtNomeRemedio.setText(remedio.getNomeRemedio());
                txtTipoRemedio.setText(remedio.getTipoRemedio());
                txtPreco.setText(String.valueOf(remedio.getPrecoRemedio()));
            }
        });
    }

    private void configurarTabela() {
        tabelaRemedio.setEditable(true);

        colSelecionado.setCellValueFactory(cellData -> {
            Remedio remedioAtual = cellData.getValue();

            remedioAtual.selecionadoProperty().addListener((obs, estavaSelecionado, estaSelecionado) -> {
                if (estaSelecionado) {
                    for (Remedio remedio : listaRemedios) {
                        if (remedio != remedioAtual) {
                            remedio.setSelecionado(false);
                        }
                    }

                    preencherCampos(remedioAtual);
                    tabelaRemedio.refresh();
                }
            });

            return remedioAtual.selecionadoProperty();
        });

        colSelecionado.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionado));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeRemedio.setCellValueFactory(new PropertyValueFactory<>("nomeRemedio"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoRemedio"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoRemedio"));

        tabelaRemedio.setItems(listaRemedios);
    }

    @FXML
    void cadastrarRemedio(ActionEvent event) {
        String nome = txtNomeRemedio.getText();
        String tipo = txtTipoRemedio.getText();
        String precoTexto = txtPreco.getText();

        if (nome.isEmpty() || tipo.isEmpty() || precoTexto.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        Double preco;

        try {
            preco = converterPreco(precoTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Digite um preço válido. Exemplo: 12.50");
            return;
        }

        Remedio remedio = new Remedio(nome, tipo, preco);

        remedioDAO.cadastrar(remedio);

        mostrarAlerta("Sucesso", "Remédio cadastrado com sucesso.");

        limparCampos();
        carregarRemedios();
    }

    @FXML
    void buscarRemedio(ActionEvent event) {
        String nomeBusca = txtNomeRemedio.getText();

        if (nomeBusca.isEmpty()) {
            carregarRemedios();
            return;
        }

        List<Remedio> resultado = remedioDAO.buscarPorNome(nomeBusca);

        listaRemedios.clear();
        listaRemedios.addAll(resultado);
    }

    @FXML
    void atualizarRemedio(ActionEvent event) {
        Remedio remedioSelecionado = getRemedioMarcado();

        if (remedioSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um remédio pelo checkbox para atualizar.");
            return;
        }

        String nome = txtNomeRemedio.getText();
        String tipo = txtTipoRemedio.getText();
        String precoTexto = txtPreco.getText();

        if (nome.isEmpty() || tipo.isEmpty() || precoTexto.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        Double preco;

        try {
            preco = converterPreco(precoTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Digite um preço válido. Exemplo: 12.50");
            return;
        }

        remedioSelecionado.setNomeRemedio(nome);
        remedioSelecionado.setTipoRemedio(tipo);
        remedioSelecionado.setPrecoRemedio(preco);

        remedioDAO.atualizar(remedioSelecionado);

        mostrarAlerta("Sucesso", "Remédio atualizado com sucesso.");

        limparCampos();
        carregarRemedios();
    }

    @FXML
    void excluirRemedio(ActionEvent event) {
        Remedio remedioSelecionado = getRemedioMarcado();

        if (remedioSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um remédio pelo checkbox para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir este remédio?");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                remedioDAO.deletar(remedioSelecionado.getId());

                mostrarAlerta("Sucesso", "Remédio excluído com sucesso.");

                limparCampos();
                carregarRemedios();
            }
        });
    }

    @FXML
    void BtVoltar(ActionEvent event) {
        try {
            App.setRoot("farmacia");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarRemedios() {
        List<Remedio> remedios = remedioDAO.listarTodos();

        listaRemedios.clear();
        listaRemedios.addAll(remedios);
    }

    private Remedio getRemedioMarcado() {
        for (Remedio remedio : listaRemedios) {
            if (remedio.isSelecionado()) {
                return remedio;
            }
        }

        return null;
    }

    private void preencherCampos(Remedio remedio) {
        txtNomeRemedio.setText(remedio.getNomeRemedio());
        txtTipoRemedio.setText(remedio.getTipoRemedio());
        txtPreco.setText(String.valueOf(remedio.getPrecoRemedio()));
    }

    private void limparCampos() {
        txtNomeRemedio.clear();
        txtTipoRemedio.clear();
        txtPreco.clear();

        for (Remedio remedio : listaRemedios) {
            remedio.setSelecionado(false);
        }

        tabelaRemedio.refresh();
    }

    private Double converterPreco(String precoTexto) {
        precoTexto = precoTexto.replace(",", ".");
        return Double.parseDouble(precoTexto);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}