package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.FuncionarioDAO;
import org.example.model.Funcionario;

import java.io.IOException;
import java.util.List;

public class FuncionarioController {

    @FXML
    private TableView<Funcionario> tabelaFuncionarios;

    @FXML
    private TableColumn<Funcionario, Boolean> colSelecionado;

    @FXML
    private TableColumn<Funcionario, Integer> colId;

    @FXML
    private TableColumn<Funcionario, String> colNome;

    @FXML
    private TableColumn<Funcionario, String> colFuncao;

    @FXML
    private TableColumn<Funcionario, String> colCPF;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtFuncao;

    @FXML
    private TextField txtCPF;

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    private ObservableList<Funcionario> listaFuncionarios = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarFuncionarios();

        tabelaFuncionarios.setOnMouseClicked(event -> {
            Funcionario funcionario = tabelaFuncionarios.getSelectionModel().getSelectedItem();

            if (funcionario != null) {
                txtNome.setText(funcionario.getNome());
                txtFuncao.setText(funcionario.getFuncao());
                txtCPF.setText(funcionario.getCpf());
            }
        });
    }

    private void configurarTabela() {
        tabelaFuncionarios.setEditable(true);

        colSelecionado.setCellValueFactory(cellData -> cellData.getValue().selecionadoProperty());
        colSelecionado.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionado));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFuncao.setCellValueFactory(new PropertyValueFactory<>("funcao"));
        colCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        tabelaFuncionarios.setItems(listaFuncionarios);
    }

    private void carregarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();

        listaFuncionarios.clear();
        listaFuncionarios.addAll(funcionarios);
    }

    @FXML
    void cadastrarFuncionario(ActionEvent event) {
        String nome = txtNome.getText();
        String funcao = txtFuncao.getText();
        String cpf = txtCPF.getText();

        if (nome.isEmpty() || funcao.isEmpty() || cpf.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        Funcionario funcionario = new Funcionario(nome, funcao, cpf);

        funcionarioDAO.cadastrar(funcionario);

        mostrarAlerta("Sucesso", "Funcionário cadastrado com sucesso.");

        limparCampos();
        carregarFuncionarios();
    }

    @FXML
    void buscarFuncionario(ActionEvent event) {
        String nome = txtNome.getText();

        List<Funcionario> resultado = funcionarioDAO.buscarPorNome(nome);

        listaFuncionarios.clear();
        listaFuncionarios.addAll(resultado);
    }

    @FXML
    void atualizarFuncionario(ActionEvent event) {
        Funcionario funcionario = getFuncionarioMarcado();

        if (funcionario == null) {
            mostrarAlerta("Erro", "Selecione um funcionário pelo checkbox.");
            return;
        }

        funcionario.setNome(txtNome.getText());
        funcionario.setFuncao(txtFuncao.getText());
        funcionario.setCpf(txtCPF.getText());

        funcionarioDAO.atualizar(funcionario);

        mostrarAlerta("Sucesso", "Funcionário atualizado com sucesso.");

        limparCampos();
        carregarFuncionarios();
    }

    @FXML
    void excluirFuncionario(ActionEvent event) {
        Funcionario funcionario = getFuncionarioMarcado();

        if (funcionario == null) {
            mostrarAlerta("Erro", "Selecione um funcionário pelo checkbox.");
            return;
        }

        funcionarioDAO.deletar(funcionario.getId());

        mostrarAlerta("Sucesso", "Funcionário excluído com sucesso.");

        limparCampos();
        carregarFuncionarios();
    }

    @FXML
    void BtVoltar(ActionEvent event) {
        try {
            App.setRoot("farmacia");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Funcionario getFuncionarioMarcado() {
        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario.isSelecionado()) {
                return funcionario;
            }
        }

        return null;
    }

    private void limparCampos() {
        txtNome.clear();
        txtFuncao.clear();
        txtCPF.clear();

        for (Funcionario funcionario : listaFuncionarios) {
            funcionario.setSelecionado(false);
        }

        tabelaFuncionarios.refresh();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}