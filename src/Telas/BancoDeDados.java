/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Telas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class BancoDeDados {

    private static final String URL = "jdbc:sqlite:cadastro.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void criarTabela() {

        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL UNIQUE,
                senha TEXT NOT NULL
            )
            """;

        try (Connection conexao = conectar();
             Statement comando = conexao.createStatement()) {

            comando.execute(sql);

            System.out.println("Banco de dados conectado!");
            System.out.println("Tabela usuarios criada/verificada!");

        } catch (SQLException erro) {

            System.out.println("Erro ao criar o banco:");
            erro.printStackTrace();
        }
    }

    public static boolean cadastrarUsuario(String nome, String senha) {

        String sql = "INSERT INTO usuarios (nome, senha) VALUES (?, ?)";

        try (Connection conexao = conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, nome);
            comando.setString(2, senha);

            comando.executeUpdate();

            return true;

        } catch (SQLException erro) {

            System.out.println("Erro ao cadastrar usuário:");
            erro.printStackTrace();

            return false;
        }
    }
    public static boolean verificarUsuario(String nome, String senha) {

    String sql = "SELECT * FROM usuarios WHERE nome = ? AND senha = ?";

    try (Connection conexao = conectar();
         PreparedStatement comando = conexao.prepareStatement(sql)) {

        comando.setString(1, nome);
        comando.setString(2, senha);

        var resultado = comando.executeQuery();

        return resultado.next();

    } catch (SQLException erro) {

        System.out.println("Erro ao verificar usuário:");
        erro.printStackTrace();

        return false;
    }
}
}