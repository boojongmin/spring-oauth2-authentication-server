package boojongmin.oauth2.authorization;

import boojongmin.oauth2.authorization.entity.Client;
import boojongmin.oauth2.authorization.entity.Member;
import boojongmin.oauth2.authorization.entity.enums.MemberStatus;
import boojongmin.oauth2.authorization.repository.ClientRepository;
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
    CommandLineRunner run(MemberRepository memberRepository, ClientRepository clientRepository) {
        return args -> {
            String password;
            password = "{noop}user";
            final Member user = Member.builder().
                    email("user").password(password).status(MemberStatus.ACTIVE)
                    .roles("USER")
                    .build();
            memberRepository.save(user);

            final int day = 60 * 24;
            final Client client = Client.builder().clientId("client1")
                    .clientSecret("{noop}client1")
                    .grantTypes("authorization_code, implicit, password, client_credentials")
                    .scopes("all")
                    .redirectURI("http://localhost:8081/hello")
                    .accessTokenValidity(day)
                    .refreshTokenValidity(day * 31).build();
            clientRepository.save(client);

        };
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
