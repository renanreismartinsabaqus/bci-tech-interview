package cl.bci.registry.user.model;

import cl.bci.registry.infra.SecureTokenGenerator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collection;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Username can not be blank")
    private String name;

    @NotBlank(message = "Email address can not be blank.")
    @Email
    private String email;

    @Size(min = 6, max = 30)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Collection<Phone> phones;

    private String token;

    private boolean isActive;

    private Instant createdAt;

    private Instant modifiedAt;

    private Instant lastLogin;

    public static User newUser(
            String name,
            String email,
            String password,
            Collection<Phone> phones
    ) {
        var now = Instant.now();
        return new User(null, name, email, password, phones, SecureTokenGenerator.generateNewToken(), true, now, null, now);
    }
}