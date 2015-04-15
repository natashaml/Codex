package com.codex.restCrud.model;

import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
@SuppressWarnings("serial")
@Entity
@Indexed
@Table(name = "project")
public class Project extends Model implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    private Long lastModified;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String synopsis;

//	@Column(name = "picture")
//	private String picture;

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String name;
//
//	private String creative;

//	private Integer rating;
//
//	@Version
//	private Integer version;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
//
//	@Fetch(FetchMode.SELECT)
//	@ManyToMany(fetch = FetchType.LAZY, cascade =
//	{ CascadeType.PERSIST })
//	@JoinTable(name = "projectTag", joinColumns =
//	{ @JoinColumn(name = "projectId") }, inverseJoinColumns =
//	{ @JoinColumn(name = "tagId") })
//	private List<Comment> tags;

    @IndexedEmbedded
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

//	public List<Comment> getComments()
//	{
//		return tags;
//	}
//
//	public void setComments(List<Comment> tags)
//	{
//		this.tags = tags;
//	}

    public List<Task> getTasks() {
        // Collections.sort(tasks);
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getTask(Integer number) {
        Iterator<Task> i = tasks.iterator();
        while (i.hasNext()) {
            Task current = i.next();
            if (current.getNumber() == number)
                return current;
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

//	public String getCreative()
//	{
//		return creative;
//	}
//
//	public void setCreative(String creative)
//	{
//		this.creative = creative;
//	}

//	public Integer getRating()
//	{
//		return rating;
//	}
//
//	public void setRating(Integer rating)
//	{
//		this.rating = rating;
//	}
//
//	public Integer getVersion()
//	{
//		return version;
//	}
//
//	public void setVersion(Integer version)
//	{
//		this.version = version;
//	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getEntityName() {
        return "project";
    }

//	public String getPicture()
//	{
//		return picture;
//	}
//
//	public void setPicture(String picture)
//	{
//		this.picture = picture;
//	}
//
//	@Override
//	public int compareTo(Project another)
//	{
//		return this.getRating() - another.getRating();
//	}

//	public String getJsPicture()
//	{
//		return StringEscapeUtils.escapeHtml(StringEscapeUtils
//				.escapeJavaScript(getPicture()));
//	}

    public List<Task> getOrderedTasks() {
        List<Task> tasksOrdered = new ArrayList<Task>(tasks.size());
        tasksOrdered.addAll(tasks);
        Collections.sort(tasksOrdered, new TaskSorter());
        return tasksOrdered;
    }
}
