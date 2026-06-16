package dao;

import model.Shampoo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShampooDAO {

    private Connection conexao;

    public ShampooDAO() {
        this.conexao = ConexaoBD.getConexao();
    }

    // ── CREATE ────────────────────────────────────────────
    public boolean cadastrar(Shampoo s) {
        String sql = "INSERT INTO shampoo (marca, tipo, tamanho) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, s.getMarca());
            ps.setString(2, s.getTipo());
            ps.setString(3, s.getTamanho());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar shampoo: " + e.getMessage());
            return false;
        }
    }

    // ── READ (todos) ──────────────────────────────────────
    public List<Shampoo> listarTodos() {
        List<Shampoo> lista = new ArrayList<>();
        String sql = "SELECT * FROM shampoo ORDER BY marca";
        try (Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar shampoos: " + e.getMessage());
        }
        return lista;
    }

    // ── READ (busca por marca ou tipo) ────────────────────
    public List<Shampoo> buscar(String termo) {
        List<Shampoo> lista = new ArrayList<>();
        String sql = "SELECT * FROM shampoo WHERE marca LIKE ? OR tipo LIKE ? ORDER BY marca";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            String like = "%" + termo + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar shampoo: " + e.getMessage());
        }
        return lista;
    }

    // ── UPDATE ────────────────────────────────────────────
    public boolean atualizar(Shampoo s) {
        String sql = "UPDATE shampoo SET marca = ?, tipo = ?, tamanho = ? WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, s.getMarca());
            ps.setString(2, s.getTipo());
            ps.setString(3, s.getTamanho());
            ps.setInt(4, s.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar shampoo: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ────────────────────────────────────────────
    public boolean deletar(int id) {
        String sql = "DELETE FROM shampoo WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar shampoo: " + e.getMessage());
            return false;
        }
    }

    // ── Mapeia ResultSet → Shampoo ────────────────────────
    private Shampoo mapear(ResultSet rs) throws SQLException {
        return new Shampoo(
            rs.getInt("id"),
            rs.getString("marca"),
            rs.getString("tipo"),
            rs.getString("tamanho")
        );
    }
}
