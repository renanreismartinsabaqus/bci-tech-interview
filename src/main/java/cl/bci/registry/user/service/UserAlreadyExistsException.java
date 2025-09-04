package cl.bci.registry.user.service;

import static java.lang.String.format;

public class UserAlreadyExistsException extends RuntimeException {

    public final String email;

    public UserAlreadyExistsException(String email) {
        super(format("User with email %s already exists", email));
        this.email = email;
    }
}
