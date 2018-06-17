package boojongmin.oauth2.authorization;

import boojongmin.oauth2.authorization.entity.Member;
import boojongmin.oauth2.authorization.entity.enums.MemberStatus;
import boojongmin.oauth2.authorization.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import static java.util.Collections.emptyList;

@SpringBootApplication
public class Application {
    @Bean
    CommandLineRunner run(MemberRepository memberRepository,
                          PasswordEncoder passwordEncoder) {
        return args -> {
            String password;
            password = passwordEncoder.encode("user");
            final Member user = Member.builder().
                    email("user").password(password).status(MemberStatus.ACTIVE)
                    .roles("USER")
                    .build();
            memberRepository.save(user);

        };
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
