package cl.bci.registry.user.rest.common;

import cl.bci.registry.user.model.Phone;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record  PhoneAPI(
        @NotNull
        @Size(min = 6, max = 12)
        String number,

        @NotNull
        @Size(min = 2, max = 2)
        String cityCode,

        @NotNull
        @Size(min = 2, max = 2)
        String countryCode
) {
    public static PhoneAPI from(Phone p) {
        return new PhoneAPI(p.getNumber(), p.getCityCode(), p.getCountryCode());
    }

    public Phone to() {
        return Phone.newPhone(number(), cityCode(), countryCode());
    }
}
