package org.example.dao;

import org.example.model.Barraca;
import org.example.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarracaDAO {

    public void cadastrar(Barraca barraca) {
        String sql = "INSERT INTO barracas (tamanho, cor, altura) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, barraca.getTamanho());
            stmt.setString(2, barraca.getCor());
            stmt.setDouble(3, barraca.getAltura());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar barraca: " + e.getMessage());
        }
    }

    public List<Barraca> listarTodos() {
        List<Barraca> barracas = new ArrayList<>();

        String sql = "SELECT id, tamanho, cor, altura FROM barracas ORDER BY id";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Barraca barraca = new Barraca();

                barraca.setId(rs.getInt("id"));
                barraca.setTamanho(rs.getString("tamanho"));
                barraca.setCor(rs.getString("cor"));
                barraca.setAltura(rs.getDouble("altura"));

                barracas.add(barraca);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar barracas: " + e.getMessage());
        }

        return barracas;
    }

    public List<Barraca> buscarPorTamanho(String tamanho) {
        List<Barraca> barracas = new ArrayList<>();

        String sql = "SELECT id, tamanho, cor, altura " +
                "FROM barracas " +
                "WHERE LOWER(tamanho) LIKE LOWER(?) " +
                "ORDER BY id";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + tamanho + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Barraca barraca = new Barraca();

                barraca.setId(rs.getInt("id"));
                barraca.setTamanho(rs.getString("tamanho"));
                barraca.setCor(rs.getString("cor"));
                barraca.setAltura(rs.getDouble("altura"));

                barracas.add(barraca);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar barraca: " + e.getMessage());
        }

        return barracas;
    }

    public void atualizar(Barraca barraca) {
        String sql = "UPDATE barracas SET tamanho = ?, cor = ?, altura = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, barraca.getTamanho());
            stmt.setString(2, barraca.getCor());
            stmt.setDouble(3, barraca.getAltura());
            stmt.setInt(4, barraca.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar barraca: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM barracas WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar barraca: " + e.getMessage());
        }
    }
}