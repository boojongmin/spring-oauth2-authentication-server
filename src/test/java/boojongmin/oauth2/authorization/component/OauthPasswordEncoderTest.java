package boojongmin.oauth2.authorization.component;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OauthPasswordEncoderTest {
    private OauthPasswordEncoder encoder;

    @Test
    public void encode() {
        encoder = new OauthPasswordEncoder("{noop}");
        final String result = encoder.encode("test");
        assertThat(result).isEqualTo("{noop}test");
    }

    @Test
    public void matches() {
        encoder = new OauthPasswordEncoder("{bcrypt}");
        final String encodedPassword = encoder.encode("test");
        final boolean result = encoder.matches("test", encodedPassword);
        assertThat(result).isTrue();
    }
}