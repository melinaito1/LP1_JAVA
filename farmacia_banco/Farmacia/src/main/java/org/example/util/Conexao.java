package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:postgresql://aws-1-us-west-2.pooler.supabase.com:5432/postgres?user=postgres.rjtrvymxkmvfnoftjdsu&password=Patinh032#@";
    private static final String USER = "postgres.rjtrvymxkmvfnoftjdsu";
    private static final String PASSWORD = "Patinh032#@";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}