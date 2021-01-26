package electonic.document.management.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Message;
import electonic.document.management.model.Task;
import electonic.document.management.model.user.User;
import electonic.document.management.model.Views;
import electonic.document.management.service.TaskService;
import electonic.document.management.service.user.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    public TaskController(TaskService taskService, ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.objectMapper = objectMapper;
    }

    @PreAuthorize("hasAuthority('LEAD')")
    @PostMapping
    public ResponseEntity<?> createTask(Task task, @AuthenticationPrincipal User user) {
        if (!taskService.addTask(task, user)) {
            return ResponseEntity.ok("Task with this name already exists");
        }

        return ResponseEntity.ok("Task was successfully created");
    }

    @PatchMapping("{task_id}")
    //TODO edit expiry date and description
    public ResponseEntity<?> editTask(@PathVariable("task_id") Task task,
                                      @RequestParam("task_name") String task_name) {
        taskService.editTask(task, task_name);
        return ResponseEntity.ok("Task was successfully edited");
    }

    @PatchMapping("{task_id}/ready_to_review")
    public ResponseEntity<?> reviewTask(@PathVariable("task_id") Task task) {
        taskService.sendTaskToReview(task);
        return ResponseEntity.ok("Task was marked as ready to review");
    }

    @GetMapping("{task_id}")
    public ResponseEntity<?> printTask(@PathVariable("task_id") Task task) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(task));
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public ResponseEntity<?> getAllTasks() throws JsonProcessingException {
        List<Task> allTasks = taskService.getAllTasks();
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.IdName.class)
                .writeValueAsString(allTasks));
    }

    @DeleteMapping("{task_id}")
    public ResponseEntity<?> deleteTask(@PathVariable("task_id") Task task) {
        taskService.deleteTask(task);
        return ResponseEntity.ok("Task was successfully deleted");
    }

    @GetMapping("with_params")
    public ResponseEntity<?> findMessages(
            @RequestParam(value = "name_contains", required = false) String subStringInName,
            @RequestParam(value = "task_description_contains", required = false) String subStringInNameTaskDescription,
            @RequestParam(value = "ready_to_review", required = false) Boolean readyToReview) throws JsonProcessingException {
        if (subStringInName == null && subStringInNameTaskDescription == null && readyToReview == null)
            return ResponseEntity.ok("No parameters were specified");
        List<Task> departmentsWithParams = taskService
                .findTasksByExample(subStringInName, subStringInNameTaskDescription, readyToReview);
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(departmentsWithParams));
    }

    //TODO method set expiry date
}
