package dao;

import model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private Connection conexao;

    public FuncionarioDAO() {
        this.conexao = ConexaoBD.getConexao();
    }

    // ── CREATE ────────────────────────────────────────────
    public boolean cadastrar(Funcionario f) {
        String sql = "INSERT INTO funcionario (nome, cargo, cpf) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCargo());
            ps.setString(3, f.getCpf());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar funcionário: " + e.getMessage());
            return false;
        }
    }

    // ── READ (todos) ──────────────────────────────────────
    public List<Funcionario> listarTodos() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario ORDER BY nome";
        try (Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return lista;
    }

    // ── READ (busca por nome ou CPF) ──────────────────────
    public List<Funcionario> buscar(String termo) {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario WHERE nome LIKE ? OR cpf LIKE ? ORDER BY nome";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            String like = "%" + termo + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário: " + e.getMessage());
        }
        return lista;
    }

    // ── UPDATE ────────────────────────────────────────────
    public boolean atualizar(Funcionario f) {
        String sql = "UPDATE funcionario SET nome = ?, cargo = ?, cpf = ? WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCargo());
            ps.setString(3, f.getCpf());
            ps.setInt(4, f.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ────────────────────────────────────────────
    public boolean deletar(int id) {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar funcionário: " + e.getMessage());
            return false;
        }
    }

    // ── Mapeia ResultSet → Funcionario ────────────────────
    private Funcionario mapear(ResultSet rs) throws SQLException {
        return new Funcionario(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("cargo"),
            rs.getString("cpf")
        );
    }
}
