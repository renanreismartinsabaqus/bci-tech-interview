package cl.bci.registry.user.service;

import cl.bci.registry.user.model.User;
import cl.bci.registry.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    UserRepository users = mock(UserRepository.class);
    UserService service;

    @BeforeEach
    void setUp() {
        service = new UserService(users);
    }

    @Test
    void shouldThrowWhenUserAlreadyExists() {
        var user = getUser();
        when(users.findByEmail("email@email.com")).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> {
            service.save(user);
        });
    }

    private static User getUser() {
        var now = Instant.now();
        return new User(
                null,
                "does not matter",
                "email@email.com",
                "does not matter",
                List.of(),
                "does not matter",
                true,
                now,
                null,
                now);
    }

}