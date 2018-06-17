package boojongmin.oauth2.authorization.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.List;

import static boojongmin.oauth2.authorization.Oauth2Utils.splitStringToList;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String grantTypes;
    private String clientId;
    private String clientSecret;
    private String redirectURI;
    private String scopes;
    private int accessTokenValidity; //minute
    private int refreshTokenValidity; //minute

    public List<String> getScopeList() {
        return splitStringToList(this.scopes);
    }
}
