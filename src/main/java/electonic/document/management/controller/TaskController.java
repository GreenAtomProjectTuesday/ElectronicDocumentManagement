package electonic.document.management.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Task;
import electonic.document.management.model.User;
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
    public ResponseEntity<String> createTask(Task task, @AuthenticationPrincipal User user) {
        if (!taskService.addTask(task, user)) {
            return ResponseEntity.ok("Task with this name already exists");
        }

        return ResponseEntity.ok("Task was successfully created");
    }

    @PreAuthorize("hasAuthority('LEAD')")
    @PostMapping("{task_id}/curators/{curators_id}/performers/{performers_id}")
    // TODO can't convert Json String to Long[] by default
    public ResponseEntity<String> setCuratorsAndPerformers(
            @PathVariable("task_id") Task task,
            @PathVariable(value = "curators_id", required = false) String curators_ids,
            @PathVariable(value = "performers_id", required = false) String performers_ids)
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
    //TODO edit expiry date
    public ResponseEntity<String> editTask(@PathVariable("task_id") Task task,
                                           @RequestParam(name = "task_name") String task_name) {
        taskService.editTask(task, task_name);
        return ResponseEntity.ok("Task was successfully edited");
    }

    //TODO consider replacing
    @PatchMapping("{task_id}/ready_to_review")
    public ResponseEntity<String> reviewTask(@PathVariable("task_id") Task task) {
        taskService.sendTaskToReview(task);
        return ResponseEntity.ok("Task was marked as ready to review");
    }

    @GetMapping("{task_id}")
    public ResponseEntity<String> printTask(@PathVariable("task_id") Task task) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullTask.class)
                .writeValueAsString(task));
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public ResponseEntity<String> getAllTasks() throws JsonProcessingException {
        List<Task> allTasks = taskService.getAllTasks();
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.IdName.class)
                .writeValueAsString(allTasks));
    }

    //TODO method set expiry date
}
