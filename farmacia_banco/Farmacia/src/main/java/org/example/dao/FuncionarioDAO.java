package org.example.dao;

import org.example.model.Funcionario;
import org.example.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void cadastrar(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (nome, funcao, cpf) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getFuncao());
            stmt.setString(3, funcionario.getCpf());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    public List<Funcionario> buscarPorNome(String nome) {
        List<Funcionario> funcionarios = new ArrayList<>();

        String sql = "SELECT id, nome, funcao, cpf FROM funcionarios WHERE LOWER(nome) LIKE LOWER(?) ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();

                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setFuncao(rs.getString("funcao"));
                funcionario.setCpf(rs.getString("cpf"));

                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar funcionário: " + e.getMessage());
        }

        return funcionarios;
    }

    public void atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET nome = ?, funcao = ?, cpf = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getFuncao());
            stmt.setString(3, funcionario.getCpf());
            stmt.setInt(4, funcionario.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar funcionário: " + e.getMessage());
        }
    }

    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();

        String sql = "SELECT id, nome, funcao, cpf FROM funcionarios ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();

                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setFuncao(rs.getString("funcao"));
                funcionario.setCpf(rs.getString("cpf"));

                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }

        return funcionarios;
    }
}