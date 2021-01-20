package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Department;
import electonic.document.management.model.Message;
import electonic.document.management.model.Task;
import electonic.document.management.model.Views;
import electonic.document.management.model.user.DepartmentEmployee;
import electonic.document.management.model.user.User;
import electonic.document.management.service.MessageService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("messages")
public class MessageController {
    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    public MessageController(MessageService messageService, ObjectMapper objectMapper) {
        this.messageService = messageService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<?> createMessage(Message message,
                                           @AuthenticationPrincipal User user) {
        messageService.createMessage(message, user);
        return ResponseEntity.ok("Message was successfully created");
    }

    @DeleteMapping("{message_id}")
    public ResponseEntity<?> deleteMessage(@PathVariable("message_id") Message message) {
        messageService.delete(message);
        return ResponseEntity.ok("Message was successfully deleted");
    }

    @PatchMapping("{message_id}")
    public ResponseEntity<?> editMessage(@PathVariable("message_id") Message message,
                                         @RequestParam("text") String text) {
        messageService.edit(message, text);
        return ResponseEntity.ok("Message was successfully edited");
    }

    @GetMapping("/in_task/{task_id}")
    public ResponseEntity<?> getAllMessagesInTask(@PathVariable("task_id") Task task) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(messageService.getAllMessagesByTask(task)));
    }

    @GetMapping("{message_id}")
    public ResponseEntity<?> getMessage(@PathVariable("message_id") Message message) throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(message));
    }

    @GetMapping("with_params")
    public ResponseEntity<?> findMessages(
            @RequestParam(value = "text_contains", required = false) String subStringInText,
            @RequestParam(value = "author_id", required = false) User user,
            @RequestParam(value = "task_id", required = false) Task task,
            @RequestParam(value = "creation_date", required = false) @DateTimeFormat LocalDateTime creationDate)
            throws JsonProcessingException {
        if (subStringInText == null && user == null && task == null && creationDate == null)
            return ResponseEntity.ok("No parameters were specified");
        List<Message> departmentsWithParams = messageService.findMessagesByExample(subStringInText, user, task, creationDate);
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(departmentsWithParams));
    }
}
