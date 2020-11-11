package electonic.document.management.service;

import electonic.document.management.model.Task;
import electonic.document.management.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean addTask(Task task) {
        Task taskFromDb = taskRepository.getTaskByTaskName(task.getTaskName());
        if (taskFromDb != null) {
            return false;
        }
        taskRepository.save(task);
        return true;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
