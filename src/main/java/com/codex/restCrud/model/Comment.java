package com.codex.restCrud.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "comment")
public class Comment extends Model implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Integer popularity;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "comments", cascade = CascadeType.PERSIST)
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public static String getEntityName() {
        return "comment";
    }

}
