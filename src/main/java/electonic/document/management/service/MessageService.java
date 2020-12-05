package electonic.document.management.service;

import electonic.document.management.model.Message;
import electonic.document.management.model.User;
import electonic.document.management.repository.MessageRepository;
import org.springframework.stereotype.Service;

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
}
