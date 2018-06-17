package boojongmin.oauth2.authorization.service;

import boojongmin.oauth2.authorization.entity.Member;
import boojongmin.oauth2.authorization.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static boojongmin.oauth2.authorization.Oauth2Utils.parseScopeArray;

@Service
public class OAuth2UserDetailsService implements UserDetailsService {
    @Autowired private MemberRepository memberRepository;
    @Autowired private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if(member == null) {
            throw new UsernameNotFoundException("Can't not find user: " + email);
        }
        String encodedPassword = passwordEncoder.encode(member.getPassword());

        return User.builder().username(member.getEmail())
                .password(encodedPassword)
                .roles(parseScopeArray(member.getRoles()))
                .build();
    }
}
