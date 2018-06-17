package boojongmin.oauth2.authorization.entity;

import boojongmin.oauth2.authorization.controller.model.OAuth2Token;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;
    private int expiresIn;
    private String scope;

    public OAuth2Token getOAuth2Token() {
        return OAuth2Token.builder().accessToken(accessToken).refreshToken(refreshToken)
                .tokenType(tokenType).expiresIn(expiresIn).scope(scope).build();
    }
}
