package nl.hva.amsserver.repositories.users;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import nl.hva.amsserver.models.user.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;


@Component
public interface UserRepositoryJPA extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
