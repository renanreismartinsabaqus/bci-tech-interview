package cl.bci.registry.user.rest;

import cl.bci.registry.user.rest.command.UserCommand;
import cl.bci.registry.user.rest.response.UserResponse;
import cl.bci.registry.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService service;

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getProductById(@PathVariable Long id) {
        var maybeUser = service.findById(id);
        return ResponseEntity.of(maybeUser.map(UserResponse::from));
    }

    @PostMapping(consumes =  APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCommand command) {
        var user = service.save(command.toDomain());
        var userResponse = UserResponse.from(user);

        return ResponseEntity
                .created(resourceLocation(userResponse))
                .body(userResponse);
    }

    private static URI resourceLocation(UserResponse userResponse) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userResponse.id())
                .toUri();
    }
}
