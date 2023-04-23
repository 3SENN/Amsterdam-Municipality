package nl.hva.amsserver.repositories.users;


import nl.hva.amsserver.models.user.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(int id);

    User findByEmail(String email);

    boolean deleteById(int id);

    boolean add(User user);

}
