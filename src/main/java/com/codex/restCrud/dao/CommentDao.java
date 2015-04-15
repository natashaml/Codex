package com.codex.restCrud.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.codex.restCrud.model.Comment;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
@Repository
public class CommentDao {
    @Autowired
    SessionFactory sessionFactory;

    @Cacheable(value = "comments")
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Comment> getComments() {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT FROM comment ORDER BY popularity DESC").list();
    }

    @Cacheable(value = "comments")
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Comment> getComments(Integer number) {
        Query q = sessionFactory.getCurrentSession().createQuery(
                "FROM comment ORDER BY popularity DESC");
        q.setMaxResults(number);
        return q.list();
    }

    @Cacheable(value = "comments")
    @Transactional
    public Comment findById(Serializable id) {
        return (Comment) sessionFactory.getCurrentSession().get(Comment.class, id);
    }

    @CacheEvict(value = "comments", allEntries = true)
    @Transactional
    public List<Comment> addTags(List<String> commentNames) {
        Iterator<String> i = commentNames.iterator();
        LinkedList<Comment> result = new LinkedList<Comment>();

        while (i.hasNext())
            result.add(addComment(i.next()));

        return result;
    }

    @CacheEvict(value = {"users", "projects", "tasks", "comments"}, allEntries = true)
    @Transactional
    public Comment addComment(String name) {
        Comment existingComment = findByName(name);
        if (existingComment == null) {
            Comment newComment = new Comment();
            newComment.setName(name);
            createComment(newComment);
            return findByName(name);
        } else
            return existingComment;
    }

    @CacheEvict(value = {"users", "projects", "tasks", "comments"}, allEntries = true)
    @Transactional
    private void createComment(Comment newComment) {
        sessionFactory.getCurrentSession().save(newComment);
    }

    @Cacheable(value = "comments")
    @Transactional
    public Comment findByName(String name) {
        return (Comment) sessionFactory.getCurrentSession()
                .createQuery("FROM comment WHERE name =?").setString(0, name)
                .uniqueResult();
    }

    @Cacheable(value = "comments")
    @Transactional
    public List<Comment> getRelatedComments(List<String> keyWords) {

        Iterator<String> i = keyWords.iterator();
        List<Comment> relatedComments = new LinkedList<Comment>();
        while (i.hasNext()) {
            String current = i.next();
            relatedComments.addAll(getCommentsLike(current));
        }
        return relatedComments;
    }

    @Cacheable(value = "comments")
    @SuppressWarnings({"unchecked", "deprecation"})
    @Transactional
    public List<Comment> getCommentsLike(String likeWhat) {
        Session session = sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Comment.class).setFetchMode("tasks",
                FetchMode.EAGER);
        return (List<Comment>) cr.add(
                Restrictions.like("name", likeWhat, MatchMode.ANYWHERE)).list();
    }

}
