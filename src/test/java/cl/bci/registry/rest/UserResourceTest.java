package cl.bci.registry.rest;

import cl.bci.registry.user.model.User;
import cl.bci.registry.user.repository.UserRepository;
import cl.bci.registry.user.rest.command.UserCommand;
import cl.bci.registry.user.rest.common.PhoneAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
@Sql(scripts = "/cleanup.sql", executionPhase = BEFORE_TEST_METHOD)
class UserResourceTest {

    @Autowired
    WebTestClient webClient;

    @Autowired
    UserRepository repository;

    @Test
    public void returnsExistingUser() {
        User savedUser = saveUser();
        webClient.get().uri("/api/users/" + savedUser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void createUser() {
        var command = new UserCommand(
                "Juan Rodriguez",
                "email@email.com",
                "super_secret",
                List.of(new PhoneAPI("123456", "11", "56"))
        );
        webClient.post().uri("/api/users")
                .bodyValue(command)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void doNotDuplicateUser() {
        var savedUser = saveUser();

        var command = new UserCommand(
                "does not matter",
                savedUser.getEmail(),
                "does not matter",
                List.of(new PhoneAPI("123456", "11", "56"))
        );
        webClient.post().uri("/api/users")
                .bodyValue(command)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(409);
    }

    private User saveUser() {
        Instant now = Instant.now();
        var user = new User(
                null,
                "Juan",
                "juan@domain.com",
                "hunter21",
                null,
                "super_secret_token",
                true,
                now,
                null,
                now
        );

        return repository.save(user);
    }
}