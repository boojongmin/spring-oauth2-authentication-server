package boojongmin.oauth2.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

import static boojongmin.oauth2.authorization.Oauth2Utils.parseScopeArray;
import static java.util.Arrays.asList;

@Service
public class AuthorizeService {
    @Resource(name = "oauthClients") private Map<String, ClientModel> clients;
    @Autowired private PasswordEncoder encoder;

    public void checkClient(String clientId, String scope, String redirectURI) {
        final String[] requestScopes = parseScopeArray(scope);
        final ClientModel c = clients.get(clientId);
        if (c == null) {
            throw new RuntimeException("올바르지 않는 client_id입니다.");
        }
        int matchCount = 0;
        final List<String> clientScopes = c.getScopes();
        for (String r : requestScopes) {
            if (clientScopes.contains(r)) matchCount++;
        }
        if (matchCount != requestScopes.length) {
            throw new RuntimeException("client scope 요청이 올바르지 않습니다.");
        }
        if (StringUtils.equals(redirectURI, c.getRedirectURI()) == false) {
            throw new RuntimeException("redirect uri 값이 바르지 않습니다.");
        }
    }
}
