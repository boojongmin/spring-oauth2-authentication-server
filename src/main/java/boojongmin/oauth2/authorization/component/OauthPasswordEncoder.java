package boojongmin.oauth2.authorization.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class OauthPasswordEncoder implements PasswordEncoder {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final String passwordPrefix;

    public OauthPasswordEncoder( @Value("${project.password-prefix}") String passwordPrefix) {
        this.passwordPrefix =  passwordPrefix;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String encryptedPassword;
        if("{noop}".equals(passwordPrefix)) {
            encryptedPassword = passwordPrefix + rawPassword;
        } else {
            encryptedPassword = passwordEncoder.encode(rawPassword);
        }
        return encryptedPassword;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
