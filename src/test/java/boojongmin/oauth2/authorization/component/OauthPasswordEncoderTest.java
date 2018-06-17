package boojongmin.oauth2.authorization.component;

import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class OauthPasswordEncoderTest {
    private PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    public void matches() {
        final boolean result1 = encoder.matches("test", "{noop}test");
        final String encodedPassword2 = encoder.encode("test");
        final boolean result2 = encoder.matches("test", encodedPassword2);
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
    }
}