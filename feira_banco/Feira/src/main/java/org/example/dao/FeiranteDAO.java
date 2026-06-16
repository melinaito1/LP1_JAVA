package org.example.dao;

import org.example.model.Feirante;
import org.example.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeiranteDAO {

    public void cadastrar(Feirante feirante) {
        String sql = "INSERT INTO feirantes (nome_feirante, local_feirante, cpf_feirante) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, feirante.getNomeFeirante());
            stmt.setString(2, feirante.getLocalFeirante());
            stmt.setString(3, feirante.getCpfFeirante());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar feirante: " + e.getMessage());
        }
    }

    public List<Feirante> listarTodos() {
        List<Feirante> feirantes = new ArrayList<>();

        String sql = "SELECT id, nome_feirante, local_feirante, cpf_feirante FROM feirantes ORDER BY nome_feirante";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Feirante feirante = new Feirante();

                feirante.setId(rs.getInt("id"));
                feirante.setNomeFeirante(rs.getString("nome_feirante"));
                feirante.setLocalFeirante(rs.getString("local_feirante"));
                feirante.setCpfFeirante(rs.getString("cpf_feirante"));

                feirantes.add(feirante);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar feirantes: " + e.getMessage());
        }

        return feirantes;
    }

    public List<Feirante> buscarPorNome(String nome) {
        List<Feirante> feirantes = new ArrayList<>();

        String sql = "SELECT id, nome_feirante, local_feirante, cpf_feirante " +
                "FROM feirantes " +
                "WHERE LOWER(nome_feirante) LIKE LOWER(?) " +
                "ORDER BY nome_feirante";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Feirante feirante = new Feirante();

                feirante.setId(rs.getInt("id"));
                feirante.setNomeFeirante(rs.getString("nome_feirante"));
                feirante.setLocalFeirante(rs.getString("local_feirante"));
                feirante.setCpfFeirante(rs.getString("cpf_feirante"));

                feirantes.add(feirante);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar feirante: " + e.getMessage());
        }

        return feirantes;
    }

    public void atualizar(Feirante feirante) {
        String sql = "UPDATE feirantes SET nome_feirante = ?, local_feirante = ?, cpf_feirante = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, feirante.getNomeFeirante());
            stmt.setString(2, feirante.getLocalFeirante());
            stmt.setString(3, feirante.getCpfFeirante());
            stmt.setInt(4, feirante.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar feirante: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM feirantes WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar feirante: " + e.getMessage());
        }
    }
}