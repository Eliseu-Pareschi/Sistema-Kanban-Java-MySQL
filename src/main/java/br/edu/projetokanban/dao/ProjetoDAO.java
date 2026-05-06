
// Parte responsável pelo CRUD de Projeto no banco de dados.
// Toda parte do SQL fica AQUI 
package br.edu.projetokanban.dao;

import br.edu.projetokanban.model.Projeto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

  // Cria um novo projeto    
    public void inserir(Projeto projeto) throws SQLException {
        String sql = "INSERT INTO projetos (nome, ativo) VALUES (?, ?)";

        // try-with-resources fecha a conexão automaticamente ao terminar
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, projeto.getNome());
            ps.setBoolean(2, projeto.isAtivo());
            ps.executeUpdate();

            // Pega o ID gerado pelo banco e coloca no objeto
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                projeto.setId(rs.getInt(1));
            }
        }
    }

    //Lista todos os projetos
    public List<Projeto> listarTodos() throws SQLException {
        String sql = "SELECT id, nome, ativo FROM projetos";
        List<Projeto> lista = new ArrayList<>();

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Para cada linha retornada, cria um objeto Projeto
            while (rs.next()) {
                Projeto p = new Projeto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getBoolean("ativo")
                );
                lista.add(p);
            }
        }
        return lista;
    }

    
    //—busca um projeto pelo ID
    public Projeto buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, ativo FROM projetos WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Projeto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getBoolean("ativo")
                );
            }
        }
        return null; // retorna null se não encontrar
    }

   // Atualiza os dados do projeto
    public void atualizar(Projeto projeto) throws SQLException {
        String sql = "UPDATE projetos SET nome = ?, ativo = ? WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, projeto.getNome());
            ps.setBoolean(2, projeto.isAtivo());
            ps.setInt(3, projeto.getId());
            ps.executeUpdate();
        }
    }

    //remove um projeto pelo ID
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM projetos WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}