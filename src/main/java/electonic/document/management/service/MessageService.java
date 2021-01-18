package electonic.document.management.service;

import electonic.document.management.model.Message;
import electonic.document.management.model.Task;
import electonic.document.management.model.user.User;
import electonic.document.management.repository.MessageRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// TODO add test
@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void createMessage(Message message, User user) {
        message.setAuthor(user);
        messageRepository.save(message);
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }

    public void edit(Message message, String text) {
        message.setText(text);
        messageRepository.save(message);
    }

    public List<Message> getAllMessagesByTask(Task task) {
        return messageRepository.getAllByTask(task);
    }

    public List<Message> findMessagesByExample(String subStringInText, User user, Task task, LocalDateTime creationDate) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("text", match -> match.contains());

        Message message = new Message();
        message.setText(subStringInText);
        message.setAuthor(user);
        message.setTask(task);
        message.setCreationDate(creationDate);

        Example<Message> messageExample = Example.of(message, matcher);
        return messageRepository.findAll(messageExample);
    }
}
