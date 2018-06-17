package boojongmin.oauth2.authorization;

import lombok.Data;

import java.util.List;

import static boojongmin.oauth2.authorization.Oauth2Utils.parseScopeArray;
import static boojongmin.oauth2.authorization.Oauth2Utils.parseScopeList;

@Data
public class ClientModel {
    private String clientId;
    private String clientSecret;
    private String scopes;
    private String redirectURI;

    public ClientModel(String clientId, String clientSecret, String scopes, String redirectURI) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scopes = scopes;
        this.redirectURI = redirectURI;
    }

    public List<String> getScopes() {
        return parseScopeList(this.scopes);
    }
}
