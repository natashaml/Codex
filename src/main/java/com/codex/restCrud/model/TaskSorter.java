package com.codex.restCrud.model;

import java.util.Comparator;

/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
public class TaskSorter implements Comparator<Task> {

    @Override
    public int compare(Task taskFirst, Task taskSecond) {
        return taskFirst.getPosition() - taskSecond.getPosition();
    }

}
