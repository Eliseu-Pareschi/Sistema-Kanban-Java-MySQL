
package br.edu.projetokanban.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//Importa as classes necessárias pra conexão com o banco

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/kanban_db" + 
            "?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";
    
    private ConnectionFactory (){}
    
    
    //metódo pra conexão com o banco
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USUARIO, SENHA);
        
    }
}
