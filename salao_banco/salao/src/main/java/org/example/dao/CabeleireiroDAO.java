package org.example.dao;

import org.example.model.Cabeleireiro;
import org.example.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CabeleireiroDAO {

    public void cadastrar(Cabeleireiro cabeleireiro) {
        String sql = "INSERT INTO cabeleireiros (nome, turno, especialidade) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cabeleireiro.getNome());
            stmt.setString(2, cabeleireiro.getTurno());
            stmt.setString(3, cabeleireiro.getEspecialidade());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cabeleireiro: " + e.getMessage());
        }
    }

    public List<Cabeleireiro> listarTodos() {
        List<Cabeleireiro> cabeleireiros = new ArrayList<>();

        String sql = "SELECT id, nome, turno, especialidade FROM cabeleireiros ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cabeleireiro cabeleireiro = new Cabeleireiro();

                cabeleireiro.setId(rs.getInt("id"));
                cabeleireiro.setNome(rs.getString("nome"));
                cabeleireiro.setTurno(rs.getString("turno"));
                cabeleireiro.setEspecialidade(rs.getString("especialidade"));

                cabeleireiros.add(cabeleireiro);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar cabeleireiros: " + e.getMessage());
        }

        return cabeleireiros;
    }

    public List<Cabeleireiro> buscarPorNome(String nome) {
        List<Cabeleireiro> cabeleireiros = new ArrayList<>();

        String sql = "SELECT id, nome, turno, especialidade " +
                "FROM cabeleireiros " +
                "WHERE LOWER(nome) LIKE LOWER(?) " +
                "ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cabeleireiro cabeleireiro = new Cabeleireiro();

                cabeleireiro.setId(rs.getInt("id"));
                cabeleireiro.setNome(rs.getString("nome"));
                cabeleireiro.setTurno(rs.getString("turno"));
                cabeleireiro.setEspecialidade(rs.getString("especialidade"));

                cabeleireiros.add(cabeleireiro);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar cabeleireiro: " + e.getMessage());
        }

        return cabeleireiros;
    }

    public void atualizar(Cabeleireiro cabeleireiro) {
        String sql = "UPDATE cabeleireiros SET nome = ?, turno = ?, especialidade = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cabeleireiro.getNome());
            stmt.setString(2, cabeleireiro.getTurno());
            stmt.setString(3, cabeleireiro.getEspecialidade());
            stmt.setInt(4, cabeleireiro.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cabeleireiro: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM cabeleireiros WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar cabeleireiro: " + e.getMessage());
        }
    }
}