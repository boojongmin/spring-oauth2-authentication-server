package boojongmin.oauth2.authorization.controller;

import boojongmin.oauth2.authorization.controller.model.OAuth2Token;
import boojongmin.oauth2.authorization.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.security.Principal;

import static boojongmin.oauth2.authorization.Oauth2Utils.splitStringToArray;

@RestController
public class Oauth2TokenController {
    @Autowired private AuthorizeService authorizeService;

    //implicit는 GET이지만 당장 지원할 계획이 없음
    // authorization_code, password 두방식만 지원
    @PostMapping(value="/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public OAuth2Token oAuth2Token(
            @RequestParam(value="grant_type", required = true) String grantType,
            @RequestParam(value="client_id", required = true) String clientId,
            @RequestParam(value="code", required = false) String code,
            @RequestParam(value="scope", required = false) String scope,
            @RequestParam(value="client_secret", required = false) String clientSecret,
            @RequestParam(value="redirect_uri", required = false) String rediectUri
    ) {
        if("authorization_code".equals(grantType)) {
            return authorizeService.authorizeCode(grantType, clientId, clientSecret, code, scope);
        }
        // TODO implement other grant types
        throw new NotImplementedException();
    }


}
