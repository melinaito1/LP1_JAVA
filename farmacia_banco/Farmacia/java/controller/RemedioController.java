package controller;

import dao.RemedioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Remedio;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RemedioController implements Initializable {

    @FXML private TextField txtNome;
    @FXML private TextField txtTipo;
    @FXML private TextField txtPreco;

    @FXML private TableView<RemedioRow> tabelaRemedios;
    @FXML private TableColumn<RemedioRow, Boolean> colCheckbox;
    @FXML private TableColumn<RemedioRow, String>  colNome;
    @FXML private TableColumn<RemedioRow, String>  colTipo;
    @FXML private TableColumn<RemedioRow, String>  colPreco;

    private final RemedioDAO dao = new RemedioDAO();
    private final ObservableList<RemedioRow> dados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColunas();
        carregarTabela();
    }

    private void configurarColunas() {
        colCheckbox.setCellValueFactory(c -> c.getValue().selecionadoProperty());
        colCheckbox.setCellFactory(CheckBoxTableCell.forTableColumn(colCheckbox));
        colCheckbox.setEditable(true);
        tabelaRemedios.setEditable(true);

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoFormatado"));

        tabelaRemedios.setItems(dados);

        tabelaRemedios.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
            if (novo != null) preencherCampos(novo.getRemedio());
        });
    }

    private void carregarTabela() {
        dados.clear();
        dao.listarTodos().forEach(r -> dados.add(new RemedioRow(r)));
    }

    @FXML
    private void cadastrar() {
        if (!validarCampos()) return;
        try {
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            Remedio r = new Remedio(txtNome.getText(), txtTipo.getText(), preco);
            if (dao.cadastrar(r)) {
                alerta(Alert.AlertType.INFORMATION, "Remédio cadastrado com sucesso!");
                limparCampos();
                carregarTabela();
            }
        } catch (NumberFormatException e) {
            alerta(Alert.AlertType.WARNING, "Preço inválido. Use apenas números (ex: 12.50).");
        }
    }

    @FXML
    private void buscar() {
        String termo = txtNome.getText().trim();
        dados.clear();
        List<Remedio> resultado = termo.isEmpty() ? dao.listarTodos() : dao.buscar(termo);
        resultado.forEach(r -> dados.add(new RemedioRow(r)));
        if (resultado.isEmpty()) alerta(Alert.AlertType.INFORMATION, "Nenhum remédio encontrado.");
    }

    @FXML
    private void atualizar() {
        List<RemedioRow> selecionados = getSelecionados();
        if (selecionados.isEmpty()) { alerta(Alert.AlertType.WARNING, "Selecione um remédio para atualizar."); return; }
        if (selecionados.size() > 1) { alerta(Alert.AlertType.WARNING, "Selecione apenas um remédio por vez."); return; }
        if (!validarCampos()) return;

        try {
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            Remedio r = selecionados.get(0).getRemedio();
            r.setNome(txtNome.getText());
            r.setTipo(txtTipo.getText());
            r.setPreco(preco);
            if (dao.atualizar(r)) {
                alerta(Alert.AlertType.INFORMATION, "Remédio atualizado!");
                limparCampos();
                carregarTabela();
            }
        } catch (NumberFormatException e) {
            alerta(Alert.AlertType.WARNING, "Preço inválido.");
        }
    }

    @FXML
    private void deletar() {
        List<RemedioRow> selecionados = getSelecionados();
        if (selecionados.isEmpty()) { alerta(Alert.AlertType.WARNING, "Selecione ao menos um remédio."); return; }

        Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
            "Deletar " + selecionados.size() + " remédio(s)?", ButtonType.YES, ButtonType.NO);
        conf.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) {
                selecionados.forEach(row -> dao.deletar(row.getRemedio().getId()));
                alerta(Alert.AlertType.INFORMATION, "Remédio(s) deletado(s)!");
                limparCampos();
                carregarTabela();
            }
        });
    }

    @FXML private void voltar() { /* navegar para menu */ }

    private List<RemedioRow> getSelecionados() {
        return dados.stream().filter(RemedioRow::isSelecionado).collect(Collectors.toList());
    }

    private void preencherCampos(Remedio r) {
        txtNome.setText(r.getNome());
        txtTipo.setText(r.getTipo());
        txtPreco.setText(String.valueOf(r.getPreco()));
    }

    private void limparCampos() { txtNome.clear(); txtTipo.clear(); txtPreco.clear(); }

    private boolean validarCampos() {
        if (txtNome.getText().isBlank() || txtTipo.getText().isBlank() || txtPreco.getText().isBlank()) {
            alerta(Alert.AlertType.WARNING, "Preencha todos os campos.");
            return false;
        }
        return true;
    }

    private void alerta(Alert.AlertType tipo, String msg) { new Alert(tipo, msg).showAndWait(); }

    public static class RemedioRow {
        private final javafx.beans.property.BooleanProperty selecionado =
            new javafx.beans.property.SimpleBooleanProperty(false);
        private final Remedio remedio;

        public RemedioRow(Remedio remedio) { this.remedio = remedio; }

        public Remedio getRemedio() { return remedio; }
        public boolean isSelecionado() { return selecionado.get(); }
        public javafx.beans.property.BooleanProperty selecionadoProperty() { return selecionado; }

        public String getNome()          { return remedio.getNome(); }
        public String getTipo()          { return remedio.getTipo(); }
        public String getPrecoFormatado(){ return String.format("R$ %.2f", remedio.getPreco()); }
    }
}
