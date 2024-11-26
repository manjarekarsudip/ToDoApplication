package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.models.Task;
import com.app.service.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping
    public String getTasks(Model model) {
    	List<Task> tasks = taskService.getAllTasks();
    	model.addAttribute("tasks", tasks);
    	return "tasks";
    }
    
    @PostMapping
    public String createTask(@RequestParam String title) {
    	taskService.createTask(title);
    	return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
    	taskService.deleteTask(id);
    	return "redirect:/tasks";
    }
    
    @GetMapping("/{id}/toggle")
    public String toggleTask(@PathVariable Long id) throws IllegalAccessException {
    	taskService.toggleTask(id);
    	return "redirect:/tasks";
    }
    
 // Edit Task - Show Edit Form with Task Data
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) throws IllegalAccessException {
        Task task = taskService.getTaskById(id); // Fetch task by ID
        model.addAttribute("task", task);       // Add task to the model
        return "edit-task";                     // Return the view for editing
    }

    // Update Task - Handle Form Submission
    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute("task") Task updatedTask) throws IllegalAccessException {
        taskService.updateTask(id, updatedTask); // Update the task
        return "redirect:/tasks";                // Redirect to the tasks list
    }


}
