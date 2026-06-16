package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    // =====================================================
    // CONFIGURE AQUI OS DADOS DO SEU BANCO DE DADOS
    // =====================================================
    private static final String URL      = "jdbc:mysql://localhost:3306/farmacia";
    private static final String USUARIO  = "root";
    private static final String SENHA    = "sua_senha";
    // =====================================================

    private static Connection conexao;

    private ConexaoBD() {}

    public static Connection getConexao() {
        if (conexao == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                System.out.println("Conexão com banco de dados estabelecida.");
            } catch (ClassNotFoundException e) {
                System.err.println("Driver MySQL não encontrado: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco: " + e.getMessage());
            }
        }
        return conexao;
    }

    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
                System.out.println("Conexão encerrada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
