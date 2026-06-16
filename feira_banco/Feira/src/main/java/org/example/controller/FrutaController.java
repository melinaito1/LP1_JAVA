package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.FrutaDAO;
import org.example.model.Fruta;

import java.io.IOException;
import java.util.List;

public class FrutaController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TextField txtPreco;

    @FXML
    private TableView<Fruta> tabelaFruta;

    @FXML
    private TableColumn<Fruta, Boolean> colSelecionado;

    @FXML
    private TableColumn<Fruta, Integer> colId;

    @FXML
    private TableColumn<Fruta, String> colNome;

    @FXML
    private TableColumn<Fruta, Integer> colQuantidade;

    @FXML
    private TableColumn<Fruta, Double> colPreco;

    private FrutaDAO frutaDAO = new FrutaDAO();

    private ObservableList<Fruta> listaFrutas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarFrutas();

        tabelaFruta.setOnMouseClicked(event -> {
            Fruta fruta = tabelaFruta.getSelectionModel().getSelectedItem();

            if (fruta != null) {
                preencherCampos(fruta);
            }
        });
    }

    private void configurarTabela() {
        tabelaFruta.setEditable(true);

        colSelecionado.setCellValueFactory(cellData -> {
            Fruta frutaAtual = cellData.getValue();

            frutaAtual.selecionadoProperty().addListener((obs, estavaSelecionado, estaSelecionado) -> {
                if (estaSelecionado) {
                    for (Fruta fruta : listaFrutas) {
                        if (fruta != frutaAtual) {
                            fruta.setSelecionado(false);
                        }
                    }

                    preencherCampos(frutaAtual);
                    tabelaFruta.refresh();
                }
            });

            return frutaAtual.selecionadoProperty();
        });

        colSelecionado.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionado));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tabelaFruta.setItems(listaFrutas);
    }

    @FXML
    void cadastrarFruta(ActionEvent event) {
        String nome = txtNome.getText();
        String quantidadeTexto = txtQuantidade.getText();
        String precoTexto = txtPreco.getText();

        if (nome.isEmpty() || quantidadeTexto.isEmpty() || precoTexto.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        int quantidade;
        Double preco;

        try {
            quantidade = Integer.parseInt(quantidadeTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Digite uma quantidade válida. Exemplo: 10");
            return;
        }

        try {
            preco = converterPreco(precoTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Digite um preço válido. Exemplo: 12.50");
            return;
        }

        Fruta fruta = new Fruta(nome, quantidade, preco);

        frutaDAO.cadastrar(fruta);

        mostrarAlerta("Sucesso", "Fruta cadastrada com sucesso.");

        limparCampos();
        carregarFrutas();
    }

    @FXML
    void buscarFruta(ActionEvent event) {
        String nomeBusca = txtNome.getText();

        if (nomeBusca.isEmpty()) {
            carregarFrutas();
            return;
        }

        List<Fruta> resultado = frutaDAO.buscarPorNome(nomeBusca);

        listaFrutas.clear();
        listaFrutas.addAll(resultado);
    }

    @FXML
    void atualizarFruta(ActionEvent event) {
        Fruta frutaSelecionada = getFrutaMarcada();

        if (frutaSelecionada == null) {
            mostrarAlerta("Erro", "Selecione uma fruta pelo checkbox para atualizar.");
            return;
        }

        String nome = txtNome.getText();
        String quantidadeTexto = txtQuantidade.getText();
        String precoTexto = txtPreco.getText();

        if (nome.isEmpty() || quantidadeTexto.isEmpty() || precoTexto.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        int quantidade;
        Double preco;

        try {
            quantidade = Integer.parseInt(quantidadeTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Digite uma quantidade válida. Exemplo: 10");
            return;
        }

        try {
            preco = converterPreco(precoTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Digite um preço válido. Exemplo: 12.50");
            return;
        }

        frutaSelecionada.setNome(nome);
        frutaSelecionada.setQuantidade(quantidade);
        frutaSelecionada.setPreco(preco);

        frutaDAO.atualizar(frutaSelecionada);

        mostrarAlerta("Sucesso", "Fruta atualizada com sucesso.");

        limparCampos();
        carregarFrutas();
    }

    @FXML
    void excluirFruta(ActionEvent event) {
        Fruta frutaSelecionada = getFrutaMarcada();

        if (frutaSelecionada == null) {
            mostrarAlerta("Erro", "Selecione uma fruta pelo checkbox para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir esta fruta?");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                frutaDAO.deletar(frutaSelecionada.getId());

                mostrarAlerta("Sucesso", "Fruta excluída com sucesso.");

                limparCampos();
                carregarFrutas();
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

    private void carregarFrutas() {
        List<Fruta> frutas = frutaDAO.listarTodos();

        listaFrutas.clear();
        listaFrutas.addAll(frutas);
    }

    private Fruta getFrutaMarcada() {
        for (Fruta fruta : listaFrutas) {
            if (fruta.isSelecionado()) {
                return fruta;
            }
        }

        return null;
    }

    private void preencherCampos(Fruta fruta) {
        txtNome.setText(fruta.getNome());
        txtQuantidade.setText(String.valueOf(fruta.getQuantidade()));
        txtPreco.setText(String.valueOf(fruta.getPreco()));
    }

    private void limparCampos() {
        txtNome.clear();
        txtQuantidade.clear();
        txtPreco.clear();

        for (Fruta fruta : listaFrutas) {
            fruta.setSelecionado(false);
        }

        tabelaFruta.refresh();
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