
package br.edu.projetokanban.service;

import br.edu.projetokanban.dao.TarefaDAO;
import br.edu.projetokanban.dao.EventoTarefaDAO;
import br.edu.projetokanban.model.*;
import br.edu.projetokanban.model.Bug.Severidade;
import br.edu.projetokanban.model.Feature.ValorNegocio;
import br.edu.projetokanban.model.Feature.Complexidade;
import br.edu.projetokanban.model.Tarefa.Status;
import java.sql.SQLException;
import java.util.List;

public class TarefaService {

    private TarefaDAO        tarefaDAO   = new TarefaDAO();
    private EventoTarefaDAO  eventoDAO   = new EventoTarefaDAO();

    // Cadastra um Bug novo
    public void cadastrarBug(int projetoId, String titulo, String descricao,
                             String responsavel, Severidade severidade,
                             boolean reproduzivel) throws SQLException {

        Bug bug = new Bug(projetoId, titulo, descricao, responsavel, severidade, reproduzivel);
        tarefaDAO.inserir(bug);

        // Registra evento de criação automaticamente
        registrarEvento(bug.getId(), "Bug criado com severidade " + severidade);
        System.out.println("Bug cadastrado! ID: " + bug.getId());
    }

    // Cadastra uma Feature nova
    public void cadastrarFeature(int projetoId, String titulo, String descricao,
                                 String responsavel, ValorNegocio valorNegocio,
                                 Complexidade complexidade) throws SQLException {

        Feature feature = new Feature(projetoId, titulo, descricao, responsavel,
                                      valorNegocio, complexidade);
        tarefaDAO.inserir(feature);

        registrarEvento(feature.getId(), "Feature criada com valor " + valorNegocio);
        System.out.println("Feature cadastrada! ID: " + feature.getId());
    }

    public List<Tarefa> listarPorProjeto(int projetoId) throws SQLException {
        return tarefaDAO.listarPorProjeto(projetoId);
    }

    public Tarefa buscarPorId(int id) throws SQLException {
        Tarefa t = tarefaDAO.buscarPorId(id);
        if (t == null) {
            throw new IllegalArgumentException("Tarefa não encontrada para o ID: " + id);
        }
        return t;
    }

    // Move a tarefa para o próximo status e registra o evento
    public void moverStatus(int tarefaId, Status novoStatus) throws SQLException {
        Tarefa tarefa = buscarPorId(tarefaId);
        Status statusAnterior = tarefa.getStatus();

        tarefa.setStatus(novoStatus);
        tarefaDAO.atualizar(tarefa);

        // Toda mudança de status gera um evento — regra de negócio obrigatória
        registrarEvento(tarefaId,
            "Status alterado: " + statusAnterior + " → " + novoStatus);

        System.out.println("Status atualizado com sucesso!");
    }

    public void excluir(int id) throws SQLException {
        buscarPorId(id); // valida se existe
        tarefaDAO.excluir(id);
        System.out.println("Tarefa excluída com sucesso!");
    }

    // RELATÓRIO 1 — backlog priorizado
    public List<Tarefa> relatorioBacklog() throws SQLException {
        return tarefaDAO.listarBacklogPriorizado();
    }

    // RELATÓRIO 2 — tarefas por responsável com totais
    public void relatorioResponsavel(String responsavel) throws SQLException {
        List<Tarefa> tarefas = tarefaDAO.listarPorResponsavel(responsavel);

        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada para: " + responsavel);
            return;
        }

        int totalPrazo = 0;
        System.out.println("\n===== Tarefas de: " + responsavel + " =====");

        for (Tarefa t : tarefas) {
            // Polimorfismo em ação: chama o método certo (Bug ou Feature) automaticamente
            System.out.println(t);
            totalPrazo += t.estimarPrazo();
        }

        System.out.println("-------------------------------");
        System.out.println("Total de tarefas : " + tarefas.size());
        System.out.println("Prazo total estimado: " + totalPrazo + " dias");
    }

    // Método auxiliar para registrar eventos — usado internamente pelo Service
    private void registrarEvento(int tarefaId, String descricao) throws SQLException {
        EventoTarefa evento = new EventoTarefa(tarefaId, descricao);
        eventoDAO.inserir(evento);
    }

    // Lista o histórico de eventos de uma tarefa
    public void listarEventos(int tarefaId) throws SQLException {
        buscarPorId(tarefaId); // valida se a tarefa existe
        List<EventoTarefa> eventos = eventoDAO.listarPorTarefa(tarefaId);

        System.out.println("\n===== Histórico da Tarefa ID " + tarefaId + " =====");
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento registrado.");
        } else {
            for (EventoTarefa e : eventos) {
                System.out.println(e);
            }
        }
    }
}