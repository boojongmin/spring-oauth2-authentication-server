package boojongmin.oauth2.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.*;

@Configuration
public class Config {

    class BeanConfig {
        @Autowired Environment env;

        @Bean
        String passwordPrefix() {
            final String[] profiles = env.getActiveProfiles();
            List devProfiles = Arrays.asList("develop");
            final long count = Arrays.asList(profiles).stream().filter(x -> devProfiles.contains(x))
                    .count();
            return count > 0 ? "{noop}" : "{bcrypt}";
        }
    }

    @EnableWebSecurity
    static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired private UserDetailsService userDetailsService;
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/h2-console", "/h2-console/**", "/oauth/token").permitAll()
                    .anyRequest().authenticated();
            //TODO remove disable options
            http.csrf().disable();
            http.headers().frameOptions().disable();
            http.formLogin();
       }
    }

    class WebMvcConfig implements WebMvcConfigurer {
        @Bean
        public LocaleResolver localeResolver() {
            SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
            sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
            return sessionLocaleResolver;
        }

        @Bean
        public LocaleChangeInterceptor localeChangeInterceptor() {
            LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
            lci.setParamName("lang");
            return lci;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(localeChangeInterceptor());
        }

        @Override
        public void addViewControllers (ViewControllerRegistry registry) {
        }
    }

    class AuthorizationServerConfig {
        @Bean Map<String, ClientModel> oauthClients() {
            final Map<String, ClientModel> map = new HashMap<>();
            final ClientModel client1 = new ClientModel("client1", "{noop}client1", "all", "http://localhost:8081/hello");
            map.put("client1", client1);
            return map;
        }

    }

}
