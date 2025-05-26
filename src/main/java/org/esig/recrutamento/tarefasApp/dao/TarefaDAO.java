package org.esig.recrutamento.tarefasApp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esig.recrutamento.tarefasApp.enums.Status;
import org.esig.recrutamento.tarefasApp.model.Tarefa;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TarefaDAO {

    @PersistenceContext(unitName = "tarefasEsig")
    private EntityManager em;

    @Transactional
    public void criar(Tarefa tarefa) {
        em.persist(tarefa);
    }

    @Transactional
    public void remover(Long tarefaId) {
        Tarefa tarefa = buscarPorId(tarefaId);
        if (tarefa != null) {
            em.remove(tarefa);
        }
    }

    @Transactional
    public Tarefa atualizar(Tarefa tarefa) {
        return em.merge(tarefa);
    }

    public Tarefa buscarPorId(Long tarefaId) {
        return em.find(Tarefa.class, tarefaId);
    }

    public List<Tarefa> buscarTodos() {
        return em.createQuery("SELECT t FROM Tarefa t ORDER BY t.id ASC", Tarefa.class)
        .getResultList();
    }

    public List<Tarefa> buscarFiltrado(Long numero, String tituloDescricao, String responsavel, Status situacao) {
        StringBuilder sb = new StringBuilder("SELECT t FROM Tarefa t WHERE 1=1");
        Map<String, Object> parametros = new HashMap<>();

        if (numero != null) {
            sb.append(" AND t.id = :numero");
            parametros.put("numero", numero);
        }
        if (tituloDescricao != null && !tituloDescricao.trim().isEmpty()) {
            sb.append(" AND (LOWER(t.titulo) LIKE LOWER(:tituloDescricao) OR LOWER(t.descricao) LIKE LOWER(:tituloDescricao))");
            parametros.put("tituloDescricao", "%" + tituloDescricao + "%");
        }
        if (responsavel != null && !responsavel.trim().isEmpty()) {
            sb.append(" AND t.responsavel = :responsavel");
            parametros.put("responsavel", responsavel);
        }
        if (situacao != null) {
            sb.append(" AND t.situacao = :situacao");
            parametros.put("situacao", situacao);
        } else {
            sb.append(" AND t.situacao = :defaultSituacao");
            parametros.put("defaultSituacao", Status.EM_ANDAMENTO);
        }

        sb.append(" ORDER BY t.id ASC");

        TypedQuery<Tarefa> query = em.createQuery(sb.toString(), Tarefa.class);
        for (Map.Entry<String, Object> param : parametros.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        return query.getResultList();
    }
}
