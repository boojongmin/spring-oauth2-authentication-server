package boojongmin.oauth2.authorization.service;

import boojongmin.oauth2.authorization.controller.model.OAuth2Token;
import boojongmin.oauth2.authorization.entity.AuthorizeAllow;
import boojongmin.oauth2.authorization.entity.Client;
import boojongmin.oauth2.authorization.entity.Member;
import boojongmin.oauth2.authorization.repository.AuthorizeAllowRepository;
import boojongmin.oauth2.authorization.repository.ClientRepository;
import boojongmin.oauth2.authorization.repository.MemberRepository;
import boojongmin.oauth2.authorization.util.HashMapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static boojongmin.oauth2.authorization.Oauth2Utils.splitStringToArray;

@Service
public class AuthorizeService {
    @Autowired private ClientRepository clientRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private AuthorizeAllowRepository authorizeAllowRepository;
    @Autowired private RedisTemplate<String, String> redisTemplate;
//    @Autowired private RedisTemplate<String, Map<String, String>> redisTemplate;
    @Autowired private TokenService tokenService;
    @Autowired private PasswordEncoder passwordEncoder;
    private final String redisCodePrefix = "oauth:authorize:code:";

    public void checkAuthrizeClient(String clientId, String scope, String redirectURI) {
        Client client = clientRepository.findByClientId(clientId);
        final String[] requestScopes = splitStringToArray(scope);
        if (client == null) {
            throw new RuntimeException("올바르지 않는 client_id입니다.");
        }
        int matchCount = 0;
        final List<String> clientScopes = client.getScopeList();
        for (String r : requestScopes) {
            if (clientScopes.contains(r)) matchCount++;
        }
        if (matchCount != requestScopes.length) {
            throw new RuntimeException("client scope 요청이 올바르지 않습니다.");
        }
        if (StringUtils.equals(redirectURI, client.getRedirectURI()) == false) {
            throw new RuntimeException("redirect uri 값이 바르지 않습니다.");
        }
    }

    public void submitAuthorize(String email, String clientId, String scope) {
        final Member member = memberRepository.findByEmail(email);
        final Client client = clientRepository.findByClientId(clientId);
        AuthorizeAllow savedAuthorizedAllow = authorizeAllowRepository.findByMemberAndClient(member, client);
        if(savedAuthorizedAllow == null) {
            final AuthorizeAllow authorizeAllow = AuthorizeAllow.builder().client(client).member(member).scope(scope).build();
            authorizeAllowRepository.save(authorizeAllow);
        }
    }

    public String createCode(String clientId, String email, String scope) {
        String seed = clientId + email;
        final String hash = DigestUtils.md5DigestAsHex(seed.getBytes());
        final Map map = HashMapBuilder.of().add("email", email).add("scope", scope).build();
        redisTemplate.opsForHash().put(redisCodePrefix + hash, "email", email);
        redisTemplate.opsForHash().put(redisCodePrefix + hash, "scope", scope);
        redisTemplate.opsForHash().getOperations().expire(redisCodePrefix + hash, 200, TimeUnit.SECONDS);
        return hash;
    }

    public OAuth2Token authorizeCode(String grantType, String clientId, String clientSecret, String code, String scope) {
        final Client client = clientRepository.findByClientId(clientId);
        if(passwordEncoder.matches(clientSecret, client.getClientSecret()) == false) {
            throw new RuntimeException("client secret이 틀렸습니다.");
        }
        final Map<String, String> map = redisTemplate.<String, String>opsForHash().entries(redisCodePrefix + code);
        if(map != null) {
            return tokenService.creatToken(clientId, map.get("email"), map.get("scope"));
        } else {
            throw new RuntimeException("code가 없습니다.");
        }
    }
}

