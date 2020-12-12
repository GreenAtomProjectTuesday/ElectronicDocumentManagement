package electonic.document.management.controller;

import electonic.document.management.model.Message;
import electonic.document.management.model.user.User;
import electonic.document.management.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody Message message,
                                           @AuthenticationPrincipal User user) {
        messageService.createMessage(message, user);
        return ResponseEntity.ok("Message was successfully created");
    }

    @DeleteMapping("{message_id}")
    public ResponseEntity<?> deleteMessage(@PathVariable("message_id") Message message) {
        messageService.delete(message);
        return ResponseEntity.ok("Message was successfully created");
    }

    @PatchMapping("{message_id}")
    public ResponseEntity<?> editMessage(@PathVariable("message_id") Message message,
                                         @RequestParam("text") String text) {
        messageService.edit(message, text);
        return ResponseEntity.ok("Message was successfully created");
    }

}
