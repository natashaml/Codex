package com.codex.restCrud.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.codex.restCrud.model.Project;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
@Repository
public class ProjectDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public Project addProject(Project project) {
        if (getLazyByName(project.getName()).isEmpty()) {
            createProject(project);
            return getLazyByName(project.getName()).get(0);
        } else
            return null;
    }

    @Transactional
    private void createProject(Project project) {
        sessionFactory.getCurrentSession().save(project);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Project> getPopular(Integer number) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Project ORDER BY rating DESC")
                .setMaxResults(number).list();
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Project> getLastAdded(Integer number) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Project ORDER BY lastModified DESC")
                .setMaxResults(number).list();
    }

    @Transactional
    public void deleteProject(Project project) {
        sessionFactory.getCurrentSession().delete(project);
    }

    @Transactional
    public Project getLazyById(Serializable id) {
        return (Project) sessionFactory.getCurrentSession().get(Project.class, id);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Project> getLazyByName(String name) {
        return (List<Project>) sessionFactory.getCurrentSession()
                .createQuery("FROM Project WHERE name =?").setString(0, name)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Project> searchByCreative(String creative) {
        return (List<Project>) sessionFactory.getCurrentSession()
                .createQuery("FROM Project WHERE creative =?")
                .setString(0, creative).list();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Project> searchBySynopsis(List<String> keyWords) {
        Session session = sessionFactory.getCurrentSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        Iterator<String> i = keyWords.iterator();
        HashSet<Project> result = new HashSet<Project>();

        while (i.hasNext()) {
            QueryBuilder qb = fullTextSession.getSearchFactory()
                    .buildQueryBuilder().forEntity(Project.class).get();
            org.apache.lucene.search.Query query = qb.keyword()
                    .onField("synopsis").matching(i.next()).createQuery();

            org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
                    query, Project.class);

            result.addAll(hibQuery.list());
        }
        return new LinkedList<Project>(result);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Project> searchByName(List<String> keyWords) {
        Iterator<String> i = keyWords.iterator();
        List<Project> result = new LinkedList<Project>();

        while (i.hasNext()) {
            Session session = sessionFactory.getCurrentSession();
            FullTextSession fullTextSession = Search
                    .getFullTextSession(session);

            QueryBuilder qb = fullTextSession.getSearchFactory()
                    .buildQueryBuilder().forEntity(Project.class).get();
            org.apache.lucene.search.Query query = qb.keyword().onField("name")
                    .matching(i.next()).createQuery();

            org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
                    query, Project.class);

            result.addAll(hibQuery.list());
        }

        return result;
    }

    @Transactional
    @SuppressWarnings("deprecation")
    public Project getEagerById(Serializable id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Project.class)
                .setFetchMode("tasks", FetchMode.EAGER)
                .add(Restrictions.idEq(id));
        return (Project) c.uniqueResult();
    }

    @Transactional
    public void updateProject(Project project) {
        sessionFactory.getCurrentSession().update(project);
    }
}
