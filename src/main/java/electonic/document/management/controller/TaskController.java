package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Task;
import electonic.document.management.model.User;
import electonic.document.management.service.TaskService;
import electonic.document.management.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("task")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public TaskController(TaskService taskService, UserService userService, ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PreAuthorize("hasAuthority('LEAD')")
    @PostMapping("create")
    public ResponseEntity<String> createTask(Task task, @AuthenticationPrincipal User user) {
        if (!taskService.addTask(task, user)) {
            return ResponseEntity.ok("Task with this name already exists");
        }

        return ResponseEntity.ok("Task was successfully created");
    }

    @PreAuthorize("hasAuthority('LEAD')")
    @PostMapping("setCuratorsAndPerformers")
    // TODO can't convert Json String to Long[] by default
    public ResponseEntity<String> setCuratorsAndPerformers(@RequestParam("task_id") Task task,
                                                           @RequestParam(value = "curators", required = false) String curators_ids,
                                                           @RequestParam(value = "performers", required = false) String performers_ids) throws JsonProcessingException {
        List<User> curators = Collections.emptyList(), performers = Collections.emptyList();
        if (curators_ids != null)
            curators = userService.getUsersByIds(objectMapper.readValue(curators_ids, Long[].class));
        if (performers_ids != null)
            performers = userService.getUsersByIds(objectMapper.readValue(performers_ids, Long[].class));

        taskService.setCurators(task, curators);
        taskService.setPerformers(task, performers);

        return ResponseEntity.ok("Curators were successfully set");
    }

    @GetMapping("getAll")
    public ResponseEntity<String> getAllTasks() throws JsonProcessingException {
        List<Task> allTasks = taskService.getAllTasks();
        return ResponseEntity.ok(objectMapper.writeValueAsString(allTasks));
    }
}
