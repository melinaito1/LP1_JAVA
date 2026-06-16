package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.ShampooDAO;
import org.example.model.Shampoo;

import java.io.IOException;
import java.util.List;

public class ShampooController {

    @FXML
    private TextField txtMarcaShampoo;

    @FXML
    private TextField txtTipoShampoo;

    @FXML
    private TextField txtPrecoShampoo;

    @FXML
    private TableView<Shampoo> tabelaShampoo;

    @FXML
    private TableColumn<Shampoo, Boolean> colSelecionado;

    @FXML
    private TableColumn<Shampoo, Integer> colId;

    @FXML
    private TableColumn<Shampoo, String> colMarcaShampoo;

    @FXML
    private TableColumn<Shampoo, String> colTipoShampoo;

    @FXML
    private TableColumn<Shampoo, Double> colPrecoShampoo;

    private ShampooDAO shampooDAO = new ShampooDAO();

    private ObservableList<Shampoo> listaShampoos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarShampoos();

        tabelaShampoo.setOnMouseClicked(event -> {
            Shampoo shampoo = tabelaShampoo.getSelectionModel().getSelectedItem();

            if (shampoo != null) {
                preencherCampos(shampoo);
            }
        });
    }

    private void configurarTabela() {
        tabelaShampoo.setEditable(true);

        colSelecionado.setCellValueFactory(cellData -> {
            Shampoo shampooAtual = cellData.getValue();

            shampooAtual.selecionadoProperty().addListener((obs, estavaSelecionado, estaSelecionado) -> {
                if (estaSelecionado) {
                    for (Shampoo shampoo : listaShampoos) {
                        if (shampoo != shampooAtual) {
                            shampoo.setSelecionado(false);
                        }
                    }

                    preencherCampos(shampooAtual);
                    tabelaShampoo.refresh();
                }
            });

            return shampooAtual.selecionadoProperty();
        });

        colSelecionado.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionado));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMarcaShampoo.setCellValueFactory(new PropertyValueFactory<>("marcaShampoo"));
        colTipoShampoo.setCellValueFactory(new PropertyValueFactory<>("tipoShampoo"));
        colPrecoShampoo.setCellValueFactory(new PropertyValueFactory<>("precoShampoo"));

        tabelaShampoo.setItems(listaShampoos);
    }

    @FXML
    void cadastrarShampoo(ActionEvent event) {
        String marca = txtMarcaShampoo.getText();
        String tipo = txtTipoShampoo.getText();
        String precoTexto = txtPrecoShampoo.getText();

        if (marca.isEmpty() || tipo.isEmpty() || precoTexto.isEmpty()) {
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

        Shampoo shampoo = new Shampoo(marca, tipo, preco);

        shampooDAO.cadastrar(shampoo);

        mostrarAlerta("Sucesso", "Shampoo cadastrado com sucesso.");

        limparCampos();
        carregarShampoos();
    }

    @FXML
    void buscarShampoo(ActionEvent event) {
        String marcaBusca = txtMarcaShampoo.getText();

        if (marcaBusca.isEmpty()) {
            carregarShampoos();
            return;
        }

        List<Shampoo> resultado = shampooDAO.buscarPorMarca(marcaBusca);

        listaShampoos.clear();
        listaShampoos.addAll(resultado);
    }

    @FXML
    void atualizarShampoo(ActionEvent event) {
        Shampoo shampooSelecionado = getShampooMarcado();

        if (shampooSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um shampoo pelo checkbox para atualizar.");
            return;
        }

        String marca = txtMarcaShampoo.getText();
        String tipo = txtTipoShampoo.getText();
        String precoTexto = txtPrecoShampoo.getText();

        if (marca.isEmpty() || tipo.isEmpty() || precoTexto.isEmpty()) {
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

        shampooSelecionado.setMarcaShampoo(marca);
        shampooSelecionado.setTipoShampoo(tipo);
        shampooSelecionado.setPrecoShampoo(preco);

        shampooDAO.atualizar(shampooSelecionado);

        mostrarAlerta("Sucesso", "Shampoo atualizado com sucesso.");

        limparCampos();
        carregarShampoos();
    }

    @FXML
    void excluirShampoo(ActionEvent event) {
        Shampoo shampooSelecionado = getShampooMarcado();

        if (shampooSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um shampoo pelo checkbox para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir este shampoo?");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                shampooDAO.deletar(shampooSelecionado.getId());

                mostrarAlerta("Sucesso", "Shampoo excluído com sucesso.");

                limparCampos();
                carregarShampoos();
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

    private void carregarShampoos() {
        List<Shampoo> shampoos = shampooDAO.listarTodos();

        listaShampoos.clear();
        listaShampoos.addAll(shampoos);
    }

    private Shampoo getShampooMarcado() {
        for (Shampoo shampoo : listaShampoos) {
            if (shampoo.isSelecionado()) {
                return shampoo;
            }
        }

        return null;
    }

    private void preencherCampos(Shampoo shampoo) {
        txtMarcaShampoo.setText(shampoo.getMarcaShampoo());
        txtTipoShampoo.setText(shampoo.getTipoShampoo());
        txtPrecoShampoo.setText(String.valueOf(shampoo.getPrecoShampoo()));
    }

    private void limparCampos() {
        txtMarcaShampoo.clear();
        txtTipoShampoo.clear();
        txtPrecoShampoo.clear();

        for (Shampoo shampoo : listaShampoos) {
            shampoo.setSelecionado(false);
        }

        tabelaShampoo.refresh();
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