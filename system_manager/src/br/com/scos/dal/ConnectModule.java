/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.scos.dal;

import java.sql.*;

/**
 *
 * @author Gustavo
 */
public class ConnectModule {

    // Este método estabelece a conexão com o banco de dados.
    public static Connection conector() {
        java.sql.Connection conexao = null;
        // Chama o driver.
        String driver = "com.mysql.cj.jdbc.Driver";
        // Armazena informações uteis sobre o banco de dados.
        String url = "jdbc:mysql://localhost:3306/dbsyscontroll";
        String user = "root";
        String password = "Grd@2018!";
        // Estabelece a conexão entre o banco e o Java.
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
