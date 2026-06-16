package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.ClienteDAO;
import org.example.model.Cliente;

import java.io.IOException;
import java.util.List;

public class ClienteController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtHora;

    @FXML
    private TextField txtServico;

    @FXML
    private TableView<Cliente> tabelaCliente;

    @FXML
    private TableColumn<Cliente, Boolean> colSelecionado;

    @FXML
    private TableColumn<Cliente, Integer> colId;

    @FXML
    private TableColumn<Cliente, String> colNome;

    @FXML
    private TableColumn<Cliente, String> colHora;

    @FXML
    private TableColumn<Cliente, String> colServico;

    private ClienteDAO clienteDAO = new ClienteDAO();

    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarClientes();

        tabelaCliente.setOnMouseClicked(event -> {
            Cliente cliente = tabelaCliente.getSelectionModel().getSelectedItem();

            if (cliente != null) {
                preencherCampos(cliente);
            }
        });
    }

    private void configurarTabela() {
        tabelaCliente.setEditable(true);

        colSelecionado.setCellValueFactory(cellData -> {
            Cliente clienteAtual = cellData.getValue();

            clienteAtual.selecionadoProperty().addListener((obs, estavaSelecionado, estaSelecionado) -> {
                if (estaSelecionado) {
                    for (Cliente cliente : listaClientes) {
                        if (cliente != clienteAtual) {
                            cliente.setSelecionado(false);
                        }
                    }

                    preencherCampos(clienteAtual);
                    tabelaCliente.refresh();
                }
            });

            return clienteAtual.selecionadoProperty();
        });

        colSelecionado.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionado));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colServico.setCellValueFactory(new PropertyValueFactory<>("servico"));

        tabelaCliente.setItems(listaClientes);
    }

    @FXML
    void cadastrarCliente(ActionEvent event) {
        String nome = txtNome.getText();
        String hora = txtHora.getText();
        String servico = txtServico.getText();

        if (nome.isEmpty() || hora.isEmpty() || servico.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        Cliente cliente = new Cliente(nome, hora, servico);

        clienteDAO.cadastrar(cliente);

        mostrarAlerta("Sucesso", "Cliente cadastrado com sucesso.");

        limparCampos();
        carregarClientes();
    }

    @FXML
    void buscarCliente(ActionEvent event) {
        String nomeBusca = txtNome.getText();

        if (nomeBusca.isEmpty()) {
            carregarClientes();
            return;
        }

        List<Cliente> resultado = clienteDAO.buscarPorNome(nomeBusca);

        listaClientes.clear();
        listaClientes.addAll(resultado);
    }

    @FXML
    void atualizarCliente(ActionEvent event) {
        Cliente clienteSelecionado = getClienteMarcado();

        if (clienteSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um cliente pelo checkbox para atualizar.");
            return;
        }

        String nome = txtNome.getText();
        String hora = txtHora.getText();
        String servico = txtServico.getText();

        if (nome.isEmpty() || hora.isEmpty() || servico.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        clienteSelecionado.setNome(nome);
        clienteSelecionado.setHora(hora);
        clienteSelecionado.setServico(servico);

        clienteDAO.atualizar(clienteSelecionado);

        mostrarAlerta("Sucesso", "Cliente atualizado com sucesso.");

        limparCampos();
        carregarClientes();
    }

    @FXML
    void excluirCliente(ActionEvent event) {
        Cliente clienteSelecionado = getClienteMarcado();

        if (clienteSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um cliente pelo checkbox para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir este cliente?");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                clienteDAO.deletar(clienteSelecionado.getId());

                mostrarAlerta("Sucesso", "Cliente excluído com sucesso.");

                limparCampos();
                carregarClientes();
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

    private void carregarClientes() {
        List<Cliente> clientes = clienteDAO.listarTodos();

        listaClientes.clear();
        listaClientes.addAll(clientes);
    }

    private Cliente getClienteMarcado() {
        for (Cliente cliente : listaClientes) {
            if (cliente.isSelecionado()) {
                return cliente;
            }
        }

        return null;
    }

    private void preencherCampos(Cliente cliente) {
        txtNome.setText(cliente.getNome());
        txtHora.setText(cliente.getHora());
        txtServico.setText(cliente.getServico());
    }

    private void limparCampos() {
        txtNome.clear();
        txtHora.clear();
        txtServico.clear();

        for (Cliente cliente : listaClientes) {
            cliente.setSelecionado(false);
        }

        tabelaCliente.refresh();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}