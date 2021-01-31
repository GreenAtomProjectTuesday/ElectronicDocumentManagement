package electonic.document.management.service;

import electonic.document.management.model.Message;
import electonic.document.management.model.RequestParametersException;
import electonic.document.management.model.Task;
import electonic.document.management.model.user.User;
import electonic.document.management.repository.TaskRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean addTask(Task task) {
        Task taskFromDb = taskRepository.getTaskByName(task.getName());
        if (taskFromDb != null) {
            return false;
        }
        task.setTaskUuid(UUID.randomUUID());
        task.setCreationDate(LocalDateTime.now());

        taskRepository.save(task);
        return true;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void editTask(Task task, String task_name) {
        task.setName(task_name);
    }

    public void sendTaskToReview(Task task) {
        task.setReadyToReview(true);
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    //TODO find by element in list of employees and documents
    public List<Task> findTasksByExample(String subStringInName, String subStringInNameTaskDescription,
                                         Boolean readyToReview) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("name", match -> match.contains())
                .withMatcher("taskDescription", match -> match.contains());

        Task task = new Task();
        task.setName(subStringInName);
        task.setTaskDescription(subStringInNameTaskDescription);
        task.setReadyToReview(readyToReview);

        Example<Task> taskExample = Example.of(task, matcher);
        return taskRepository.findAll(taskExample);
    }

    public Task getTaskById(Long id) throws RequestParametersException {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new RequestParametersException("Task with such id does not exists!");
        }
        return optionalTask.get();
    }
}
