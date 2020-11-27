package electonic.document.management.repository;

import electonic.document.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

//    <T> List<T> findAllBy(Class<T> type);
}
