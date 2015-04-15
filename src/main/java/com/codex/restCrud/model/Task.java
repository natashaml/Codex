package com.codex.restCrud.model;


import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
@SuppressWarnings("serial")
@Indexed(index = "fulltext")
@Entity
@Table(name = "task")
public class Task extends Model implements Comparable<Task>, Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private short number;

    private short position;

    private Long lastModified;

    private String title;

    @Column(name = "text", columnDefinition = "TEXT")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String text;

    @Version
    private Integer version;


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.LAZY, cascade =
            {CascadeType.PERSIST})
    @JoinTable(name = "taskComment", joinColumns =
            {@JoinColumn(name = "projectId")}, inverseJoinColumns =
            {@JoinColumn(name = "commentId")})
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
    }

    public short getPosition() {
        return position;
    }

    public void setPosition(short position) {
        this.position = position;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int compareTo(Task c) {
        return this.number - c.number;
    }

    public static String getEntityName() {
        return "task";
    }

    public String getJsText() {
        return StringEscapeUtils.escapeHtml(StringEscapeUtils.escapeJavaScript(getText()));
    }
}
