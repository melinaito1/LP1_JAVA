package controller;

import dao.ShampooDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Shampoo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShampooController implements Initializable {

    @FXML private TextField txtMarca;
    @FXML private TextField txtTipo;
    @FXML private TextField txtTamanho;

    @FXML private TableView<ShampooRow> tabelaShampoos;
    @FXML private TableColumn<ShampooRow, Boolean> colCheckbox;
    @FXML private TableColumn<ShampooRow, String>  colMarca;
    @FXML private TableColumn<ShampooRow, String>  colTipo;
    @FXML private TableColumn<ShampooRow, String>  colTamanho;

    private final ShampooDAO dao = new ShampooDAO();
    private final ObservableList<ShampooRow> dados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColunas();
        carregarTabela();
    }

    private void configurarColunas() {
        colCheckbox.setCellValueFactory(c -> c.getValue().selecionadoProperty());
        colCheckbox.setCellFactory(CheckBoxTableCell.forTableColumn(colCheckbox));
        colCheckbox.setEditable(true);
        tabelaShampoos.setEditable(true);

        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colTamanho.setCellValueFactory(new PropertyValueFactory<>("tamanho"));

        tabelaShampoos.setItems(dados);

        tabelaShampoos.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
            if (novo != null) preencherCampos(novo.getShampoo());
        });
    }

    private void carregarTabela() {
        dados.clear();
        dao.listarTodos().forEach(s -> dados.add(new ShampooRow(s)));
    }

    @FXML
    private void cadastrar() {
        if (!validarCampos()) return;
        Shampoo s = new Shampoo(txtMarca.getText(), txtTipo.getText(), txtTamanho.getText());
        if (dao.cadastrar(s)) {
            alerta(Alert.AlertType.INFORMATION, "Shampoo cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        }
    }

    @FXML
    private void buscar() {
        String termo = txtMarca.getText().trim();
        dados.clear();
        List<Shampoo> resultado = termo.isEmpty() ? dao.listarTodos() : dao.buscar(termo);
        resultado.forEach(s -> dados.add(new ShampooRow(s)));
        if (resultado.isEmpty()) alerta(Alert.AlertType.INFORMATION, "Nenhum shampoo encontrado.");
    }

    @FXML
    private void atualizar() {
        List<ShampooRow> selecionados = getSelecionados();
        if (selecionados.isEmpty()) { alerta(Alert.AlertType.WARNING, "Selecione um shampoo para atualizar."); return; }
        if (selecionados.size() > 1) { alerta(Alert.AlertType.WARNING, "Selecione apenas um shampoo por vez."); return; }
        if (!validarCampos()) return;

        Shampoo s = selecionados.get(0).getShampoo();
        s.setMarca(txtMarca.getText());
        s.setTipo(txtTipo.getText());
        s.setTamanho(txtTamanho.getText());

        if (dao.atualizar(s)) {
            alerta(Alert.AlertType.INFORMATION, "Shampoo atualizado!");
            limparCampos();
            carregarTabela();
        }
    }

    @FXML
    private void deletar() {
        List<ShampooRow> selecionados = getSelecionados();
        if (selecionados.isEmpty()) { alerta(Alert.AlertType.WARNING, "Selecione ao menos um shampoo."); return; }

        Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
            "Deletar " + selecionados.size() + " shampoo(s)?", ButtonType.YES, ButtonType.NO);
        conf.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) {
                selecionados.forEach(row -> dao.deletar(row.getShampoo().getId()));
                alerta(Alert.AlertType.INFORMATION, "Shampoo(s) deletado(s)!");
                limparCampos();
                carregarTabela();
            }
        });
    }

    @FXML private void voltar() { /* navegar para menu */ }

    private List<ShampooRow> getSelecionados() {
        return dados.stream().filter(ShampooRow::isSelecionado).collect(Collectors.toList());
    }

    private void preencherCampos(Shampoo s) {
        txtMarca.setText(s.getMarca());
        txtTipo.setText(s.getTipo());
        txtTamanho.setText(s.getTamanho());
    }

    private void limparCampos() { txtMarca.clear(); txtTipo.clear(); txtTamanho.clear(); }

    private boolean validarCampos() {
        if (txtMarca.getText().isBlank() || txtTipo.getText().isBlank() || txtTamanho.getText().isBlank()) {
            alerta(Alert.AlertType.WARNING, "Preencha todos os campos.");
            return false;
        }
        return true;
    }

    private void alerta(Alert.AlertType tipo, String msg) { new Alert(tipo, msg).showAndWait(); }

    public static class ShampooRow {
        private final javafx.beans.property.BooleanProperty selecionado =
            new javafx.beans.property.SimpleBooleanProperty(false);
        private final Shampoo shampoo;

        public ShampooRow(Shampoo shampoo) { this.shampoo = shampoo; }

        public Shampoo getShampoo() { return shampoo; }
        public boolean isSelecionado() { return selecionado.get(); }
        public javafx.beans.property.BooleanProperty selecionadoProperty() { return selecionado; }

        public String getMarca()   { return shampoo.getMarca(); }
        public String getTipo()    { return shampoo.getTipo(); }
        public String getTamanho() { return shampoo.getTamanho(); }
    }
}
