package br.edu.projetokanban.dao;

import br.edu.projetokanban.model.EventoTarefa;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventoTarefaDAO {

    // Insere uma informação vinculada a uma tarefa
    public void inserir(EventoTarefa evento) throws SQLException {
        String sql = "INSERT INTO eventos_tarefa (tarefa_id, descricao, datahora) VALUES (?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, evento.getTarefaId());
            ps.setString(2, evento.getDescricao());
            // Converte LocalDateTime para o tipo que o JDBC entende
            ps.setTimestamp(3, Timestamp.valueOf(evento.getDataHora()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                evento.setId(rs.getInt(1));
            }
        }
    }

    //recupera todos os acontecimentos da tarefa e os organiza em ordem cronologica
    public List<EventoTarefa> listarPorTarefa(int tarefaId) throws SQLException {
        String sql = "SELECT * FROM eventos_tarefa WHERE tarefa_id = ? ORDER BY datahora";
        List<EventoTarefa> lista = new ArrayList<>();

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, tarefaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EventoTarefa e = new EventoTarefa(
                    rs.getInt("id"),
                    rs.getInt("tarefa_id"),
                    rs.getString("descricao"),
                    // Converte Timestamp do banco para LocalDateTime do Java
                    rs.getTimestamp("datahora").toLocalDateTime()
                );
                lista.add(e);
            }
        }
        return lista;
    }
}