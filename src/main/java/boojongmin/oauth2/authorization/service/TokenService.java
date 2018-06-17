package boojongmin.oauth2.authorization.service;

import boojongmin.oauth2.authorization.controller.model.OAuth2Token;
import boojongmin.oauth2.authorization.entity.Client;
import boojongmin.oauth2.authorization.entity.Token;
import boojongmin.oauth2.authorization.repository.ClientRepository;
import boojongmin.oauth2.authorization.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
public class TokenService {
    @Autowired private TokenRepository tokenRepository;
    @Autowired private ClientRepository clientRepository;
    public OAuth2Token creatToken(String clientId, String email, String scope) {
        final Client client = clientRepository.findByClientId(clientId);
        final byte[] accessTokenSeed = (clientId + email + new Date().getTime()).getBytes();
        final byte[] refreshTokenSeed = (email + clientId + new Date().getTime()).getBytes();
        final String accessToken = DigestUtils.md5DigestAsHex(accessTokenSeed);
        final String refreshToken = DigestUtils.md5DigestAsHex(refreshTokenSeed);
        final Token token = Token.builder().accessToken(accessToken)
                .refreshToken(refreshToken)
                //TODO expire 대상이 access? refresh?
                .tokenType("Bearer")
                .expiresIn(client.getAccessTokenValidity())
                .scope(scope)
                .build();
        tokenRepository.save(token);
        return token.getOAuth2Token();
    }
}
