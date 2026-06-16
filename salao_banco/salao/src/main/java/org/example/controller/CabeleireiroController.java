package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.CabeleireiroDAO;
import org.example.model.Cabeleireiro;

import java.io.IOException;
import java.util.List;

public class CabeleireiroController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTurno;

    @FXML
    private TextField txtEspecialidade;

    @FXML
    private TableView<Cabeleireiro> tabelaCabeleireiro;

    @FXML
    private TableColumn<Cabeleireiro, Boolean> colSelecionado;

    @FXML
    private TableColumn<Cabeleireiro, Integer> colId;

    @FXML
    private TableColumn<Cabeleireiro, String> colNome;

    @FXML
    private TableColumn<Cabeleireiro, String> colTurno;

    @FXML
    private TableColumn<Cabeleireiro, String> colEspecialidade;

    private CabeleireiroDAO cabeleireiroDAO = new CabeleireiroDAO();

    private ObservableList<Cabeleireiro> listaCabeleireiros = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarCabeleireiros();

        tabelaCabeleireiro.setOnMouseClicked(event -> {
            Cabeleireiro cabeleireiro = tabelaCabeleireiro.getSelectionModel().getSelectedItem();

            if (cabeleireiro != null) {
                preencherCampos(cabeleireiro);
            }
        });
    }

    private void configurarTabela() {
        tabelaCabeleireiro.setEditable(true);

        colSelecionado.setCellValueFactory(cellData -> {
            Cabeleireiro cabeleireiroAtual = cellData.getValue();

            cabeleireiroAtual.selecionadoProperty().addListener((obs, estavaSelecionado, estaSelecionado) -> {
                if (estaSelecionado) {
                    for (Cabeleireiro cabeleireiro : listaCabeleireiros) {
                        if (cabeleireiro != cabeleireiroAtual) {
                            cabeleireiro.setSelecionado(false);
                        }
                    }

                    preencherCampos(cabeleireiroAtual);
                    tabelaCabeleireiro.refresh();
                }
            });

            return cabeleireiroAtual.selecionadoProperty();
        });

        colSelecionado.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionado));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        tabelaCabeleireiro.setItems(listaCabeleireiros);
    }

    @FXML
    void cadastrarCabeleireiro(ActionEvent event) {
        String nome = txtNome.getText();
        String turno = txtTurno.getText();
        String especialidade = txtEspecialidade.getText();

        if (nome.isEmpty() || turno.isEmpty() || especialidade.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        Cabeleireiro cabeleireiro = new Cabeleireiro(nome, turno, especialidade);

        cabeleireiroDAO.cadastrar(cabeleireiro);

        mostrarAlerta("Sucesso", "Cabeleireiro cadastrado com sucesso.");

        limparCampos();
        carregarCabeleireiros();
    }

    @FXML
    void buscarCabeleireiro(ActionEvent event) {
        String nomeBusca = txtNome.getText();

        if (nomeBusca.isEmpty()) {
            carregarCabeleireiros();
            return;
        }

        List<Cabeleireiro> resultado = cabeleireiroDAO.buscarPorNome(nomeBusca);

        listaCabeleireiros.clear();
        listaCabeleireiros.addAll(resultado);
    }

    @FXML
    void atualizarCabeleireiro(ActionEvent event) {
        Cabeleireiro cabeleireiroSelecionado = getCabeleireiroMarcado();

        if (cabeleireiroSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um cabeleireiro pelo checkbox para atualizar.");
            return;
        }

        String nome = txtNome.getText();
        String turno = txtTurno.getText();
        String especialidade = txtEspecialidade.getText();

        if (nome.isEmpty() || turno.isEmpty() || especialidade.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        cabeleireiroSelecionado.setNome(nome);
        cabeleireiroSelecionado.setTurno(turno);
        cabeleireiroSelecionado.setEspecialidade(especialidade);

        cabeleireiroDAO.atualizar(cabeleireiroSelecionado);

        mostrarAlerta("Sucesso", "Cabeleireiro atualizado com sucesso.");

        limparCampos();
        carregarCabeleireiros();
    }

    @FXML
    void excluirCabeleireiro(ActionEvent event) {
        Cabeleireiro cabeleireiroSelecionado = getCabeleireiroMarcado();

        if (cabeleireiroSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um cabeleireiro pelo checkbox para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir este cabeleireiro?");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                cabeleireiroDAO.deletar(cabeleireiroSelecionado.getId());

                mostrarAlerta("Sucesso", "Cabeleireiro excluído com sucesso.");

                limparCampos();
                carregarCabeleireiros();
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

    private void carregarCabeleireiros() {
        List<Cabeleireiro> cabeleireiros = cabeleireiroDAO.listarTodos();

        listaCabeleireiros.clear();
        listaCabeleireiros.addAll(cabeleireiros);
    }

    private Cabeleireiro getCabeleireiroMarcado() {
        for (Cabeleireiro cabeleireiro : listaCabeleireiros) {
            if (cabeleireiro.isSelecionado()) {
                return cabeleireiro;
            }
        }

        return null;
    }

    private void preencherCampos(Cabeleireiro cabeleireiro) {
        txtNome.setText(cabeleireiro.getNome());
        txtTurno.setText(cabeleireiro.getTurno());
        txtEspecialidade.setText(cabeleireiro.getEspecialidade());
    }

    private void limparCampos() {
        txtNome.clear();
        txtTurno.clear();
        txtEspecialidade.clear();

        for (Cabeleireiro cabeleireiro : listaCabeleireiros) {
            cabeleireiro.setSelecionado(false);
        }

        tabelaCabeleireiro.refresh();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}