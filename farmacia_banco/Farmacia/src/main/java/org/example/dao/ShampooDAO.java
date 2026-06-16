package org.example.dao;

import org.example.model.Shampoo;
import org.example.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShampooDAO {

    public void cadastrar(Shampoo shampoo) {
        String sql = "INSERT INTO shampoos (marca_shampoo, tipo_shampoo, preco_shampoo) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shampoo.getMarcaShampoo());
            stmt.setString(2, shampoo.getTipoShampoo());
            stmt.setDouble(3, shampoo.getPrecoShampoo());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar shampoo: " + e.getMessage());
        }
    }

    public List<Shampoo> listarTodos() {
        List<Shampoo> shampoos = new ArrayList<>();

        String sql = "SELECT id, marca_shampoo, tipo_shampoo, preco_shampoo FROM shampoos ORDER BY marca_shampoo";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Shampoo shampoo = new Shampoo();

                shampoo.setId(rs.getInt("id"));
                shampoo.setMarcaShampoo(rs.getString("marca_shampoo"));
                shampoo.setTipoShampoo(rs.getString("tipo_shampoo"));
                shampoo.setPrecoShampoo(rs.getDouble("preco_shampoo"));

                shampoos.add(shampoo);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar shampoos: " + e.getMessage());
        }

        return shampoos;
    }

    public List<Shampoo> buscarPorMarca(String marca) {
        List<Shampoo> shampoos = new ArrayList<>();

        String sql = "SELECT id, marca_shampoo, tipo_shampoo, preco_shampoo " +
                "FROM shampoos " +
                "WHERE LOWER(marca_shampoo) LIKE LOWER(?) " +
                "ORDER BY marca_shampoo";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + marca + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Shampoo shampoo = new Shampoo();

                shampoo.setId(rs.getInt("id"));
                shampoo.setMarcaShampoo(rs.getString("marca_shampoo"));
                shampoo.setTipoShampoo(rs.getString("tipo_shampoo"));
                shampoo.setPrecoShampoo(rs.getDouble("preco_shampoo"));

                shampoos.add(shampoo);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar shampoo: " + e.getMessage());
        }

        return shampoos;
    }

    public void atualizar(Shampoo shampoo) {
        String sql = "UPDATE shampoos SET marca_shampoo = ?, tipo_shampoo = ?, preco_shampoo = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shampoo.getMarcaShampoo());
            stmt.setString(2, shampoo.getTipoShampoo());
            stmt.setDouble(3, shampoo.getPrecoShampoo());
            stmt.setInt(4, shampoo.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar shampoo: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM shampoos WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar shampoo: " + e.getMessage());
        }
    }
}