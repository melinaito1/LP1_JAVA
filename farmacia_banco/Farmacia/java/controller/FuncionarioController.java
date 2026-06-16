package controller;

import dao.FuncionarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Funcionario;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FuncionarioController implements Initializable {

    // ── Campos do formulário ──────────────────────────────
    @FXML private TextField txtNome;
    @FXML private TextField txtCargo;
    @FXML private TextField txtCpf;

    // ── Tabela ────────────────────────────────────────────
    @FXML private TableView<FuncionarioRow> tabelaFuncionarios;
    @FXML private TableColumn<FuncionarioRow, Boolean>  colCheckbox;
    @FXML private TableColumn<FuncionarioRow, String>   colNome;
    @FXML private TableColumn<FuncionarioRow, String>   colCargo;
    @FXML private TableColumn<FuncionarioRow, String>   colCpf;

    private final FuncionarioDAO dao = new FuncionarioDAO();
    private final ObservableList<FuncionarioRow> dados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColunas();
        carregarTabela();
    }

    // ── Configura colunas ─────────────────────────────────
    private void configurarColunas() {
        colCheckbox.setCellValueFactory(c -> c.getValue().selecionadoProperty());
        colCheckbox.setCellFactory(CheckBoxTableCell.forTableColumn(colCheckbox));
        colCheckbox.setEditable(true);
        tabelaFuncionarios.setEditable(true);

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        tabelaFuncionarios.setItems(dados);

        // Clique na linha preenche os campos para edição
        tabelaFuncionarios.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
            if (novo != null) preencherCampos(novo.getFuncionario());
        });
    }

    // ── Carrega / recarrega tabela ────────────────────────
    private void carregarTabela() {
        dados.clear();
        dao.listarTodos().forEach(f -> dados.add(new FuncionarioRow(f)));
    }

    // ── CADASTRAR ─────────────────────────────────────────
    @FXML
    private void cadastrar() {
        if (!validarCampos()) return;

        Funcionario f = new Funcionario(txtNome.getText(), txtCargo.getText(), txtCpf.getText());
        if (dao.cadastrar(f)) {
            alerta(Alert.AlertType.INFORMATION, "Funcionário cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        } else {
            alerta(Alert.AlertType.ERROR, "Erro ao cadastrar funcionário.");
        }
    }

    // ── BUSCAR ────────────────────────────────────────────
    @FXML
    private void buscar() {
        String termo = txtNome.getText().trim();
        dados.clear();
        List<Funcionario> resultado = termo.isEmpty() ? dao.listarTodos() : dao.buscar(termo);
        resultado.forEach(f -> dados.add(new FuncionarioRow(f)));

        if (resultado.isEmpty()) {
            alerta(Alert.AlertType.INFORMATION, "Nenhum funcionário encontrado.");
        }
    }

    // ── ATUALIZAR ─────────────────────────────────────────
    @FXML
    private void atualizar() {
        List<FuncionarioRow> selecionados = getSelecionados();

        if (selecionados.isEmpty()) {
            alerta(Alert.AlertType.WARNING, "Selecione ao menos um funcionário na tabela para atualizar.");
            return;
        }
        if (selecionados.size() > 1) {
            alerta(Alert.AlertType.WARNING, "Selecione apenas um funcionário para atualizar por vez.");
            return;
        }
        if (!validarCampos()) return;

        Funcionario f = selecionados.get(0).getFuncionario();
        f.setNome(txtNome.getText());
        f.setCargo(txtCargo.getText());
        f.setCpf(txtCpf.getText());

        if (dao.atualizar(f)) {
            alerta(Alert.AlertType.INFORMATION, "Funcionário atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        } else {
            alerta(Alert.AlertType.ERROR, "Erro ao atualizar funcionário.");
        }
    }

    // ── DELETAR ───────────────────────────────────────────
    @FXML
    private void deletar() {
        List<FuncionarioRow> selecionados = getSelecionados();

        if (selecionados.isEmpty()) {
            alerta(Alert.AlertType.WARNING, "Selecione ao menos um funcionário para deletar.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION,
            "Deseja deletar " + selecionados.size() + " funcionário(s)?",
            ButtonType.YES, ButtonType.NO);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.YES) {
                selecionados.forEach(row -> dao.deletar(row.getFuncionario().getId()));
                alerta(Alert.AlertType.INFORMATION, "Funcionário(s) deletado(s) com sucesso!");
                limparCampos();
                carregarTabela();
            }
        });
    }

    // ── VOLTAR ────────────────────────────────────────────
    @FXML
    private void voltar() {
        // Implemente a navegação para o menu principal aqui
        // Exemplo: StageManager.trocarTela("menu.fxml");
    }

    // ── Helpers ───────────────────────────────────────────
    private List<FuncionarioRow> getSelecionados() {
        return dados.stream().filter(r -> r.isSelecionado()).collect(Collectors.toList());
    }

    private void preencherCampos(Funcionario f) {
        txtNome.setText(f.getNome());
        txtCargo.setText(f.getCargo());
        txtCpf.setText(f.getCpf());
    }

    private void limparCampos() {
        txtNome.clear();
        txtCargo.clear();
        txtCpf.clear();
    }

    private boolean validarCampos() {
        if (txtNome.getText().isBlank() || txtCargo.getText().isBlank() || txtCpf.getText().isBlank()) {
            alerta(Alert.AlertType.WARNING, "Preencha todos os campos.");
            return false;
        }
        return true;
    }

    private void alerta(Alert.AlertType tipo, String mensagem) {
        new Alert(tipo, mensagem).showAndWait();
    }

    // ── Classe interna: linha com checkbox ────────────────
    public static class FuncionarioRow {
        private final javafx.beans.property.BooleanProperty selecionado =
            new javafx.beans.property.SimpleBooleanProperty(false);
        private final Funcionario funcionario;

        public FuncionarioRow(Funcionario funcionario) {
            this.funcionario = funcionario;
        }

        public Funcionario getFuncionario() { return funcionario; }
        public boolean isSelecionado() { return selecionado.get(); }
        public javafx.beans.property.BooleanProperty selecionadoProperty() { return selecionado; }

        // Propriedades para a tabela
        public String getNome()  { return funcionario.getNome(); }
        public String getCargo() { return funcionario.getCargo(); }
        public String getCpf()   { return funcionario.getCpf(); }
    }
}
