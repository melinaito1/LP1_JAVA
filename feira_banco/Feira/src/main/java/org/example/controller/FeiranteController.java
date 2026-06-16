package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.FeiranteDAO;
import org.example.model.Feirante;

import java.io.IOException;
import java.util.List;

public class FeiranteController {

    @FXML
    private TextField txtNomeFeirante;

    @FXML
    private TextField txtLocalFeirante;

    @FXML
    private TextField txtCpfFeirante;

    @FXML
    private TableView<Feirante> tabelaFeirante;

    @FXML
    private TableColumn<Feirante, Boolean> colSelecionado;

    @FXML
    private TableColumn<Feirante, Integer> colId;

    @FXML
    private TableColumn<Feirante, String> colNomeFeirante;

    @FXML
    private TableColumn<Feirante, String> colLocalFeirante;

    @FXML
    private TableColumn<Feirante, String> colCpfFeirante;

    private FeiranteDAO feiranteDAO = new FeiranteDAO();

    private ObservableList<Feirante> listaFeirantes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarFeirantes();

        tabelaFeirante.setOnMouseClicked(event -> {
            Feirante feirante = tabelaFeirante.getSelectionModel().getSelectedItem();

            if (feirante != null) {
                preencherCampos(feirante);
            }
        });
    }

    private void configurarTabela() {
        tabelaFeirante.setEditable(true);

        colSelecionado.setCellValueFactory(cellData -> {
            Feirante feiranteAtual = cellData.getValue();

            feiranteAtual.selecionadoProperty().addListener((obs, estavaSelecionado, estaSelecionado) -> {
                if (estaSelecionado) {
                    for (Feirante feirante : listaFeirantes) {
                        if (feirante != feiranteAtual) {
                            feirante.setSelecionado(false);
                        }
                    }

                    preencherCampos(feiranteAtual);
                    tabelaFeirante.refresh();
                }
            });

            return feiranteAtual.selecionadoProperty();
        });

        colSelecionado.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionado));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeFeirante.setCellValueFactory(new PropertyValueFactory<>("nomeFeirante"));
        colLocalFeirante.setCellValueFactory(new PropertyValueFactory<>("localFeirante"));
        colCpfFeirante.setCellValueFactory(new PropertyValueFactory<>("cpfFeirante"));

        tabelaFeirante.setItems(listaFeirantes);
    }

    @FXML
    void cadastrarFeirante(ActionEvent event) {
        String nome = txtNomeFeirante.getText();
        String local = txtLocalFeirante.getText();
        String cpf = txtCpfFeirante.getText();

        if (nome.isEmpty() || local.isEmpty() || cpf.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        Feirante feirante = new Feirante(nome, local, cpf);

        feiranteDAO.cadastrar(feirante);

        mostrarAlerta("Sucesso", "Feirante cadastrado com sucesso.");

        limparCampos();
        carregarFeirantes();
    }

    @FXML
    void buscarFeirante(ActionEvent event) {
        String nomeBusca = txtNomeFeirante.getText();

        if (nomeBusca.isEmpty()) {
            carregarFeirantes();
            return;
        }

        List<Feirante> resultado = feiranteDAO.buscarPorNome(nomeBusca);

        listaFeirantes.clear();
        listaFeirantes.addAll(resultado);
    }

    @FXML
    void atualizarFeirante(ActionEvent event) {
        Feirante feiranteSelecionado = getFeiranteMarcado();

        if (feiranteSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um feirante pelo checkbox para atualizar.");
            return;
        }

        String nome = txtNomeFeirante.getText();
        String local = txtLocalFeirante.getText();
        String cpf = txtCpfFeirante.getText();

        if (nome.isEmpty() || local.isEmpty() || cpf.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        feiranteSelecionado.setNomeFeirante(nome);
        feiranteSelecionado.setLocalFeirante(local);
        feiranteSelecionado.setCpfFeirante(cpf);

        feiranteDAO.atualizar(feiranteSelecionado);

        mostrarAlerta("Sucesso", "Feirante atualizado com sucesso.");

        limparCampos();
        carregarFeirantes();
    }

    @FXML
    void excluirFeirante(ActionEvent event) {
        Feirante feiranteSelecionado = getFeiranteMarcado();

        if (feiranteSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um feirante pelo checkbox para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir este feirante?");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                feiranteDAO.deletar(feiranteSelecionado.getId());

                mostrarAlerta("Sucesso", "Feirante excluído com sucesso.");

                limparCampos();
                carregarFeirantes();
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

    private void carregarFeirantes() {
        List<Feirante> feirantes = feiranteDAO.listarTodos();

        listaFeirantes.clear();
        listaFeirantes.addAll(feirantes);
    }

    private Feirante getFeiranteMarcado() {
        for (Feirante feirante : listaFeirantes) {
            if (feirante.isSelecionado()) {
                return feirante;
            }
        }

        return null;
    }

    private void preencherCampos(Feirante feirante) {
        txtNomeFeirante.setText(feirante.getNomeFeirante());
        txtLocalFeirante.setText(feirante.getLocalFeirante());
        txtCpfFeirante.setText(feirante.getCpfFeirante());
    }

    private void limparCampos() {
        txtNomeFeirante.clear();
        txtLocalFeirante.clear();
        txtCpfFeirante.clear();

        for (Feirante feirante : listaFeirantes) {
            feirante.setSelecionado(false);
        }

        tabelaFeirante.refresh();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}