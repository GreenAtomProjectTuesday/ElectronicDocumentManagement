package electonic.document.management.service;

import electonic.document.management.model.Task;
import electonic.document.management.model.User;
import electonic.document.management.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean addTask(Task task, User user) {
        Task taskFromDb = taskRepository.getTaskByTaskName(task.getTaskName());
        if (taskFromDb != null) {
            return false;
        }
        task.setCreationDate(LocalDateTime.now());
        task.setCreator(user);

        taskRepository.save(task);
        return true;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void setCurators(Task task, List<User> curators) {
        if (!curators.isEmpty()) {
            task.setCurators(curators);
            taskRepository.save(task);
        }
    }

    public void setPerformers(Task task, List<User> performers) {
        if (!performers.isEmpty()) {
            task.setCurators(performers);
            taskRepository.save(task);
        }
    }

    public void editTask(Task task, String task_name) {
        task.setTaskName(task_name);
    }

    //TODO create additional more table for ready to review tasks?
    public void sendTaskToReview(Task task) {
        task.setReadyToReview(true);
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
}
