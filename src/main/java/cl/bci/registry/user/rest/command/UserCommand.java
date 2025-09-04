package cl.bci.registry.user.rest.command;

import cl.bci.registry.user.model.User;
import cl.bci.registry.user.rest.common.PhoneAPI;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.List;

public record UserCommand(
        @NotBlank(message = "Username can not be blank")
        String name,

        @NotBlank(message = "Email address can not be blank.")
        @Email(message = "Email address should be valid.")
        String email,

        @Size(min = 6, max = 30)
        String password,

        @Valid
        Collection<PhoneAPI> phones
){

    public UserCommand {
        if (phones == null) {
            phones = List.of();
        }
    }

    public User toDomain() {
        var phones = this.phones
                .stream()
                .map(PhoneAPI::to)
                .toList();

        return User.newUser(name, email, password, phones);
    }
}



