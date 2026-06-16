package org.example.dao;

import org.example.model.Fruta;
import org.example.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FrutaDAO {

    public void cadastrar(Fruta fruta) {
        String sql = "INSERT INTO frutas (nome, quantidade, preco) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fruta.getNome());
            stmt.setInt(2, fruta.getQuantidade());
            stmt.setDouble(3, fruta.getPreco());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar fruta: " + e.getMessage());
        }
    }

    public List<Fruta> listarTodos() {
        List<Fruta> frutas = new ArrayList<>();

        String sql = "SELECT id, nome, quantidade, preco FROM frutas ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fruta fruta = new Fruta();

                fruta.setId(rs.getInt("id"));
                fruta.setNome(rs.getString("nome"));
                fruta.setQuantidade(rs.getInt("quantidade"));
                fruta.setPreco(rs.getDouble("preco"));

                frutas.add(fruta);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar frutas: " + e.getMessage());
        }

        return frutas;
    }

    public List<Fruta> buscarPorNome(String nome) {
        List<Fruta> frutas = new ArrayList<>();

        String sql = "SELECT id, nome, quantidade, preco " +
                "FROM frutas " +
                "WHERE LOWER(nome) LIKE LOWER(?) " +
                "ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Fruta fruta = new Fruta();

                fruta.setId(rs.getInt("id"));
                fruta.setNome(rs.getString("nome"));
                fruta.setQuantidade(rs.getInt("quantidade"));
                fruta.setPreco(rs.getDouble("preco"));

                frutas.add(fruta);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar fruta: " + e.getMessage());
        }

        return frutas;
    }

    public void atualizar(Fruta fruta) {
        String sql = "UPDATE frutas SET nome = ?, quantidade = ?, preco = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fruta.getNome());
            stmt.setInt(2, fruta.getQuantidade());
            stmt.setDouble(3, fruta.getPreco());
            stmt.setInt(4, fruta.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar fruta: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM frutas WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar fruta: " + e.getMessage());
        }
    }
}