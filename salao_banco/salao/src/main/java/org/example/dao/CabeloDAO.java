package org.example.dao;

import org.example.model.Cabelo;
import org.example.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CabeloDAO {

    public void cadastrar(Cabelo cabelo) {
        String sql = "INSERT INTO cabelos (tipo, cor, comprimento) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cabelo.getTipo());
            stmt.setString(2, cabelo.getCor());
            stmt.setString(3, cabelo.getComprimento());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cabelo: " + e.getMessage());
        }
    }

    public List<Cabelo> listarTodos() {
        List<Cabelo> cabelos = new ArrayList<>();

        String sql = "SELECT id, tipo, cor, comprimento FROM cabelos ORDER BY tipo";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cabelo cabelo = new Cabelo();

                cabelo.setId(rs.getInt("id"));
                cabelo.setTipo(rs.getString("tipo"));
                cabelo.setCor(rs.getString("cor"));
                cabelo.setComprimento(rs.getString("comprimento"));

                cabelos.add(cabelo);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar cabelos: " + e.getMessage());
        }

        return cabelos;
    }

    public List<Cabelo> buscarPorTipo(String tipo) {
        List<Cabelo> cabelos = new ArrayList<>();

        String sql = "SELECT id, tipo, cor, comprimento " +
                "FROM cabelos " +
                "WHERE LOWER(tipo) LIKE LOWER(?) " +
                "ORDER BY tipo";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + tipo + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cabelo cabelo = new Cabelo();

                cabelo.setId(rs.getInt("id"));
                cabelo.setTipo(rs.getString("tipo"));
                cabelo.setCor(rs.getString("cor"));
                cabelo.setComprimento(rs.getString("comprimento"));

                cabelos.add(cabelo);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar cabelo: " + e.getMessage());
        }

        return cabelos;
    }

    public void atualizar(Cabelo cabelo) {
        String sql = "UPDATE cabelos SET tipo = ?, cor = ?, comprimento = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cabelo.getTipo());
            stmt.setString(2, cabelo.getCor());
            stmt.setString(3, cabelo.getComprimento());
            stmt.setInt(4, cabelo.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cabelo: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM cabelos WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar cabelo: " + e.getMessage());
        }
    }
}