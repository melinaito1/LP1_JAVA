package dao;

import model.Remedio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RemedioDAO {

    private Connection conexao;

    public RemedioDAO() {
        this.conexao = ConexaoBD.getConexao();
    }

    // ── CREATE ────────────────────────────────────────────
    public boolean cadastrar(Remedio r) {
        String sql = "INSERT INTO remedio (nome, tipo, preco) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, r.getNome());
            ps.setString(2, r.getTipo());
            ps.setDouble(3, r.getPreco());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar remédio: " + e.getMessage());
            return false;
        }
    }

    // ── READ (todos) ──────────────────────────────────────
    public List<Remedio> listarTodos() {
        List<Remedio> lista = new ArrayList<>();
        String sql = "SELECT * FROM remedio ORDER BY nome";
        try (Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar remédios: " + e.getMessage());
        }
        return lista;
    }

    // ── READ (busca por nome ou tipo) ─────────────────────
    public List<Remedio> buscar(String termo) {
        List<Remedio> lista = new ArrayList<>();
        String sql = "SELECT * FROM remedio WHERE nome LIKE ? OR tipo LIKE ? ORDER BY nome";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            String like = "%" + termo + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar remédio: " + e.getMessage());
        }
        return lista;
    }

    // ── UPDATE ────────────────────────────────────────────
    public boolean atualizar(Remedio r) {
        String sql = "UPDATE remedio SET nome = ?, tipo = ?, preco = ? WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, r.getNome());
            ps.setString(2, r.getTipo());
            ps.setDouble(3, r.getPreco());
            ps.setInt(4, r.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar remédio: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ────────────────────────────────────────────
    public boolean deletar(int id) {
        String sql = "DELETE FROM remedio WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar remédio: " + e.getMessage());
            return false;
        }
    }

    // ── Mapeia ResultSet → Remedio ────────────────────────
    private Remedio mapear(ResultSet rs) throws SQLException {
        return new Remedio(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("tipo"),
            rs.getDouble("preco")
        );
    }
}
