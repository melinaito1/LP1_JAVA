package org.example.dao;

import org.example.model.Remedio;
import org.example.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RemedioDAO {

    public void cadastrar(Remedio remedio) {
        String sql = "INSERT INTO remedios (nome_remedio, tipo_remedio, preco_remedio) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, remedio.getNomeRemedio());
            stmt.setString(2, remedio.getTipoRemedio());
            stmt.setDouble(3, remedio.getPrecoRemedio());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar remédio: " + e.getMessage());
        }
    }

    public List<Remedio> listarTodos() {
        List<Remedio> remedios = new ArrayList<>();

        String sql = "SELECT id, nome_remedio, tipo_remedio, preco_remedio FROM remedios ORDER BY nome_remedio";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Remedio remedio = new Remedio();

                remedio.setId(rs.getInt("id"));
                remedio.setNomeRemedio(rs.getString("nome_remedio"));
                remedio.setTipoRemedio(rs.getString("tipo_remedio"));
                remedio.setPrecoRemedio(rs.getDouble("preco_remedio"));

                remedios.add(remedio);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar remédios: " + e.getMessage());
        }

        return remedios;
    }

    public List<Remedio> buscarPorNome(String nome) {
        List<Remedio> remedios = new ArrayList<>();

        String sql = "SELECT id, nome_remedio, tipo_remedio, preco_remedio " +
                "FROM remedios " +
                "WHERE LOWER(nome_remedio) LIKE LOWER(?) " +
                "ORDER BY nome_remedio";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Remedio remedio = new Remedio();

                remedio.setId(rs.getInt("id"));
                remedio.setNomeRemedio(rs.getString("nome_remedio"));
                remedio.setTipoRemedio(rs.getString("tipo_remedio"));
                remedio.setPrecoRemedio(rs.getDouble("preco_remedio"));

                remedios.add(remedio);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar remédio: " + e.getMessage());
        }

        return remedios;
    }

    public void atualizar(Remedio remedio) {
        String sql = "UPDATE remedios SET nome_remedio = ?, tipo_remedio = ?, preco_remedio = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, remedio.getNomeRemedio());
            stmt.setString(2, remedio.getTipoRemedio());
            stmt.setDouble(3, remedio.getPrecoRemedio());
            stmt.setInt(4, remedio.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar remédio: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM remedios WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar remédio: " + e.getMessage());
        }
    }
}