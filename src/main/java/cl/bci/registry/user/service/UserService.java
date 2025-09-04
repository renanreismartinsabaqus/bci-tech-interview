package cl.bci.registry.user.service;

import cl.bci.registry.user.model.User;
import cl.bci.registry.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository users;

    public UserService(UserRepository users) {
        this.users = users;
    }

    public User save(User user) {
        var maybeExistingUser = users.findByEmail(user.getEmail());
        maybeExistingUser.ifPresent(u -> {
            throw new UserAlreadyExistsException(u.getEmail());
        });

        return users.save(user);
    }

    public Optional<User> findById(Long id) {
        return users.findById(id);
    }
}
