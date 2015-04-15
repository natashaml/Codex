package com.codex.restCrud.service;

/**
 * @author Natasha Levkovich
 * @since 10.04.15
 */
public class EditTaskForm {
    private String title;

    private String taskText;

    private String indexChanges;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getIndexChanges() {
        return indexChanges;
    }

    public void setIndexChanges(String indexChanges) {
        this.indexChanges = indexChanges;
    }
}
