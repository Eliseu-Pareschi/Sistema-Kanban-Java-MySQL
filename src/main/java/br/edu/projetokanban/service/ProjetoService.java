// ============================================================
// service/ProjetoService.java
// Regras de negócio e validações de Projeto ficam AQUI.
// O DAO só sabe falar com o banco — quem decide a lógica é o Service.
// ============================================================
package br.edu.projetokanban.service;

import br.edu.projetokanban.dao.ProjetoDAO;
import br.edu.projetokanban.model.Projeto;
import java.sql.SQLException;
import java.util.List;

public class ProjetoService {

    // Service usa o DAO — nunca acessa o banco diretamente
    private ProjetoDAO projetoDAO = new ProjetoDAO();

    public void cadastrar(String nome) throws SQLException {
        // Validação de negócio fica no Service, não no DAO
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do projeto é obrigatório.");
        }
        Projeto projeto = new Projeto(nome);
        projetoDAO.inserir(projeto);
        System.out.println("Projeto cadastrado com sucesso! ID: " + projeto.getId());
    }

    public List<Projeto> listarTodos() throws SQLException {
        return projetoDAO.listarTodos();
    }

    public Projeto buscarPorId(int id) throws SQLException {
        Projeto p = projetoDAO.buscarPorId(id);
        if (p == null) {
            throw new IllegalArgumentException("Projeto não encontrado para o ID: " + id);
        }
        return p;
    }

    public void atualizar(int id, String novoNome) throws SQLException {
        Projeto p = buscarPorId(id); // já valida se existe
        p.setNome(novoNome);
        projetoDAO.atualizar(p);
        System.out.println("Projeto atualizado com sucesso!");
    }

    public void excluir(int id) throws SQLException {
        buscarPorId(id); // valida se existe antes de excluir
        projetoDAO.excluir(id);
        System.out.println("Projeto excluído com sucesso!");
    }
}