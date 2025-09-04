package cl.bci.registry.user.rest.response;

import cl.bci.registry.user.model.User;
import cl.bci.registry.user.rest.common.PhoneAPI;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public record UserResponse(
        Long id,
        String name,
        String email,
        Set<PhoneAPI> phones
) {
    public static UserResponse from(User user) {
        var phones = user.getPhones()
                .stream()
                .map(PhoneAPI::from)
                .collect(toSet());

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), phones);
    }
}
