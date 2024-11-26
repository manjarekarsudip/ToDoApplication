package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.models.Task;
import com.app.repository.TaskRespository;

@Service
public class TaskService {
	
	private final TaskRespository taskRespository;

    TaskService(TaskRespository taskRespository) {
        this.taskRespository = taskRespository;
    }
	
	public List<Task> getAllTasks() {
		
		return taskRespository.findAll();
	}

	public void createTask(String title) {
		Task task = new Task();
		task.setTitle(title);
		task.setCompleted(false);
		taskRespository.save(task);

	}

	public void deleteTask(Long id) {
		taskRespository.deleteById(id);
	}

	public void toggleTask(Long id) throws IllegalAccessException {
		Task task = taskRespository.findById(id).orElseThrow(() -> new IllegalAccessException("Invalid Task Id"));
		task.setCompleted(!task.isCompleted());
		taskRespository.save(task);
	}

	public Task getTaskById(Long id) throws IllegalAccessException {
		return taskRespository.findById(id)
                .orElseThrow(() -> new IllegalAccessException("Task not found with ID: " + id));
	}

	public void updateTask(Long id, Task updatedTask) throws IllegalAccessException {
		Task existingTask = taskRespository.findById(id)
                .orElseThrow(() -> new IllegalAccessException("Task not found with ID: " + id));

        // Update the fields you want to allow
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setCompleted(updatedTask.isCompleted());

        taskRespository.save(existingTask); // Save the updated task
		
	}

	
}
