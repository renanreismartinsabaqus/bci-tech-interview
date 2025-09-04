package cl.bci.registry.infra;

import java.security.SecureRandom;
import java.util.Base64;

public final class SecureTokenGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    private static final int TOKEN_BYTES = 32;

    private SecureTokenGenerator() {
        throw new UnsupportedOperationException();
    }


    public static String generateNewToken() {
        byte[] randomBytes = new byte[TOKEN_BYTES];

        secureRandom.nextBytes(randomBytes);

        return base64Encoder.encodeToString(randomBytes);
    }
}