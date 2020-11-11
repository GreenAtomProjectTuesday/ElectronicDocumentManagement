package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Task;
import electonic.document.management.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("task")
public class TaskController {

    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    public TaskController(TaskService taskService, ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("create")
    public ResponseEntity<?> createTask(Task task) {
        if (!taskService.addTask(task)) {
            return ResponseEntity.ok("Task with this name already exists");
        }
        return ResponseEntity.ok("Task was successfully created");
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAllTasks() throws JsonProcessingException {
        List<Task> allTasks = taskService.getAllTasks();
        return ResponseEntity.ok(objectMapper.writeValueAsString(allTasks));
    }
}
