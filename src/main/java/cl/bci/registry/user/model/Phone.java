package cl.bci.registry.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users_phones")
public class Phone {
        @Id
        @GeneratedValue(strategy = IDENTITY)
        private Long id;

        @NotNull
        @Size(min=6, max=12)
        private String number;

        @NotNull
        @Size(min=2, max=2)
        private String cityCode;

        @NotNull
        @Size(min=2, max=2)
        private String countryCode;

    public Phone(Long id, String number, String cityCode, String countryCode) {
        this.id = id;
        this.number = number;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
    }

    public static Phone newPhone(String number, String cityCode, String countryCode) {
        return new Phone(null, number, cityCode, countryCode);
    }
}
