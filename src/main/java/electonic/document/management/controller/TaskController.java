package electonic.document.management.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Task;
import electonic.document.management.model.user.User;
import electonic.document.management.model.Views;
import electonic.document.management.service.TaskService;
import electonic.document.management.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public TaskController(TaskService taskService, UserService userService, ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    //TODO PreAuthorize not working
    @PreAuthorize("hasAuthority('LEAD')")
    @PostMapping
    public ResponseEntity<?> createTask(Task task, @AuthenticationPrincipal User user) {
        if (!taskService.addTask(task, user)) {
            return ResponseEntity.ok("Task with this name already exists");
        }

        return ResponseEntity.ok("Task was successfully created");
    }

    @PreAuthorize("hasAuthority('LEAD')")
    @PostMapping("{task_id}/curators/performers/")
    // TODO can't convert Json String to Long[] by default
    public ResponseEntity<?> setCuratorsAndPerformers(
            @PathVariable("task_id") Task task,
            @RequestParam(value = "curators_id", required = false) String curators_ids,
            @RequestParam(value = "performers_id", required = false) String performers_ids)
            throws JsonProcessingException {
        List<User> curators = Collections.emptyList(), performers = Collections.emptyList();
        if (curators_ids != null)
            curators = userService.getUsersByIds(objectMapper.readValue(curators_ids, Long[].class));
        if (performers_ids != null)
            performers = userService.getUsersByIds(objectMapper.readValue(performers_ids, Long[].class));

        taskService.setCurators(task, curators);
        taskService.setPerformers(task, performers);

        return ResponseEntity.ok("Curators were successfully set");
    }

    @PatchMapping("{task_id}")
    //TODO edit expiry date and description
    public ResponseEntity<?> editTask(@PathVariable("task_id") Task task,
                                           @RequestParam("task_name") String task_name) {
        taskService.editTask(task, task_name);
        return ResponseEntity.ok("Task was successfully edited");
    }

    //TODO consider replacing
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

    //TODO method set expiry date
}
