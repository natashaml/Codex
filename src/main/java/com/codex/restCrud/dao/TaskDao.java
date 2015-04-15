package com.codex.restCrud.dao;

import com.codex.restCrud.model.Task;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
@Repository
public class TaskDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public Task findById(Serializable id) {
        return (Task) sessionFactory.getCurrentSession().get(Task.class,
                id);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Task> searchByText(List<String> keyWords) {
        Session session = sessionFactory.getCurrentSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        Iterator<String> i = keyWords.iterator();
        HashSet<Task> result = new HashSet<Task>();

        while (i.hasNext()) {
            QueryBuilder qb = fullTextSession.getSearchFactory()
                    .buildQueryBuilder().forEntity(Task.class).get();
            org.apache.lucene.search.Query query = qb.keyword().onField("text")
                    .matching(i.next()).createQuery();

            org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
                    query, Task.class);

            result.addAll(hibQuery.list());
        }
        return new ArrayList<Task>(result);
    }

    @Transactional
    public void updateTask(Task updatedTask) {
        Session session = sessionFactory.getCurrentSession();

        String queryText = "UPDATE Task SET title = :title,"
                + " text = :text, lastModified = :lastModified,"
                + " version = :version WHERE id = :id";
        Query query = session.createQuery(queryText);
        query.setParameter("title", updatedTask.getTitle());
        query.setParameter("text", updatedTask.getText());
        query.setParameter("lastModified", System.currentTimeMillis());
        query.setParameter("version", updatedTask.getVersion());
        query.setParameter("id", updatedTask.getId());

        query.executeUpdate();
    }

    @Transactional
    public void deleteById(Integer taskId) {
        Session session = sessionFactory.getCurrentSession();

        Task ch = (Task) session.get(Task.class, taskId);
        session.delete(ch);

    }

    @Transactional
    public void addNewTask(Task newTask) {
        Session session = sessionFactory.getCurrentSession();
        session.save(newTask);
    }
}
