package com.codex.restCrud.service;

import com.codex.restCrud.dao.ProjectDao;
import com.codex.restCrud.dao.TaskDao;
import com.codex.restCrud.dao.UserDao;
import com.codex.restCrud.model.Task;
import com.codex.restCrud.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

/**
 * @author Natasha Levkovich
 * @since 11.04.15
 */
@Service
public class EditProjectProcessor {
    @Autowired
    ProjectDao projectDao;

    @Autowired
    TaskDao taskDao;

    @Autowired
    UserDao userDao;

    @Autowired
    SecurityProcessor securityProcessor;

    // http://localhost:8080/restCrud/users/{userId}/{projectId}
    public String getEditProjectPage(Principal principal, Integer userId,
                                     Integer projectId, ModelMap modelMap, String action) {
        Project project = projectDao.getEagerById(projectId);
        if (!securityProcessor.isMyEditorPage(principal, userId)
                || project == null)
            return "redirect:/";

        modelMap.addAttribute("project", project);

        if (action != null) {
            return performAction(modelMap, principal, action, project, 0, userId, projectId);
        }

        if (project.getTasks().isEmpty())
            return "edit";

        Task startTask = project.getTasks().get(0);

        modelMap.addAttribute("currentTask", startTask);

        return "redirect:/users/" + userId + "/" + projectId + "/"
                + startTask.getId();
    }

    // http://localhost:8080/restCrud/users/{userId}/{projectId}/{taskId}
    // POST
    public String saveTask(EditTaskForm saveTaskForm,
                           Principal principal, Integer userId, Integer projectId,
                           Integer taskId, ModelMap modelMap) {
        Project project = projectDao.getEagerById(projectId);
        if (!securityProcessor.isMyEditorPage(principal, userId)
                || project == null)
            return "redirect:/";

        String indexes = saveTaskForm.getIndexChanges();
        updatePosition(project, indexes);

        modelMap.addAttribute("user",
                userDao.getLazyByName(principal.getName()));
        modelMap.addAttribute("project", project);
        modelMap.addAttribute("currentTask",
                saveTask(taskId, saveTaskForm));

        return "edit";
    }


    // http://localhost:8080/restCrud/users/{userId}/{projectId}/{taskId}
    public String editTask(ModelMap modelMap, Principal principal,
                           Integer userId, Integer projectId, Integer taskId, String action) {
        if (taskId == 0)
            return "redirect:/users/" + userId + "/" + projectId;

        Project project = projectDao.getEagerById(projectId);
        if (!securityProcessor.isMyEditorPage(principal, userId)
                || project == null)
            return "redirect:/";

        if (action != null) {
            return performAction(modelMap, principal, action, project, taskId, userId,
                    projectId);
        }

        setTask(modelMap, principal, project, taskId);

        return "edit";
    }

    private void setTask(ModelMap modelMap, Principal principal, Project project,
                         Integer taskId) {
        Task startTask = taskDao.findById(taskId);
        if (startTask != null) {
            modelMap.addAttribute("user",
                    userDao.getLazyByName(principal.getName()));
            modelMap.addAttribute("project", project);
            modelMap.addAttribute("currentTask", startTask);
        }

    }

    private String performAction(ModelMap modelMap, Principal principal, String action, Project project,
                                 Integer taskId, Integer userId, Integer projectId) {
        if (action.equals("delete")) {
            projectDao.updateProject(project);
            taskDao.deleteById(taskId);

        } else if (action.equals("addTask")) {
            addTask(project);
        } else if (action.equals("redactor")) {
            return redactorPicture(modelMap, principal, project, userId, projectId);
        }
        return "redirect:/users/" + userId + "/" + projectId;
    }

    private void setBook(ModelMap modelMap, Principal principal, Project project) {
        if (project != null) {
            modelMap.addAttribute("user",
                    userDao.getLazyByName(principal.getName()));
            modelMap.addAttribute("project", project);
        }

    }

    private String redactorPicture(ModelMap modelMap, Principal principal, Project project, Integer userId, Integer bookId) {
        setBook(modelMap, principal, project);
        return "redactor";
    }

    private Task saveTask(Integer id, EditTaskForm taskForm) {
        Task updatedTask = taskDao.findById(id);

        if (updatedTask == null)
            return null;

        updatedTask.setTitle(taskForm.getTitle());
        updatedTask.setText(taskForm.getTaskText());
        updatedTask.setVersion(updatedTask.getVersion() + 1);

        taskDao.updateTask(updatedTask);

        return taskDao.findById(id);
    }

    private Short lastTaskNumber(Project project) {
        List<Task> tasks = project.getTasks();
        Iterator<Task> i = tasks.iterator();
        Task cur;
        if (!i.hasNext())
            return 0;
        do {
            cur = i.next();
        } while (i.hasNext());
        return cur.getNumber();
    }

    private boolean addTask(Project project) {
        if (project == null)
            return false;

        Task newTask = new Task();

        newTask.setProject(project);
        newTask.setTitle("New Task");
        newTask.setVersion(1);
        newTask.setLastModified(System.currentTimeMillis());
        newTask.setNumber((short) (lastTaskNumber(project) + 1));
        newTask.setPosition((short) (lastTaskNumber(project) + 1));

        projectDao.updateProject(project);
        taskDao.addNewTask(newTask);

        return true;
    }

    private void updatePosition(Project project, String indexes) {
        List<Task> tasks = project.getTasks();
        String[] numbers = indexes.split(",");
        for (int i = 0; i < numbers.length; i++) {
            short number = Short.parseShort(numbers[i]);
            tasks.get(i).setPosition(number);
        }
        projectDao.updateProject(project);
    }
}
