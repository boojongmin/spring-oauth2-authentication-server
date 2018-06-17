package boojongmin.oauth2.authorization.controller;

import boojongmin.oauth2.authorization.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static boojongmin.oauth2.authorization.Oauth2Utils.splitStringToArray;

@Controller
public class Oauth2Controller {
    @Autowired private AuthorizeService authorizeService;

    @GetMapping(value="/oauth/authorize"/*, params = "respose_type=code"*/)
    // 참고 https://oauth2.thephpleague.com/authorization-server/auth-code-grant/
    public ModelAndView authorization(ModelAndView mv,
                                      @RequestParam("response_type") String responseType,
                                      @RequestParam("client_id") String clientId,
                                      @RequestParam("redirect_uri") String redirectURI,
                                      @RequestParam("scope") String scope,
                                      //TODO csrf가 없으면 redirect시켜서 강제로 값을 세팅하게끔 처리
                                      @RequestParam(value="csrf", required = false) String csrf
    ) {
        authorizeService.checkAuthrizeClient(clientId, scope, redirectURI);
        final String[] scopes = splitStringToArray(scope);
        mv.addObject("scopes", scopes);
        mv.addObject("scope", scope);
        mv.addObject("clientId", clientId);

        mv.addObject("redirect_uri", redirectURI);
        mv.addObject("response_type", responseType);
        mv.setViewName("authorize");
        return mv;
    }

    @PostMapping("/oauth/authorize/submit")
    public String authorizeSubmit( Principal principal,
                                        @RequestParam("client_id") String clientId,
                                        @RequestParam("redirect_uri") String redirectURI,
                                        @RequestParam("scope") String scope,
                                        //TODO csrf가 없으면 redirect시켜서 강제로 값을 세팅하게끔 처리
                                        @RequestParam(value="csrf", required = false) String csrf) {
        authorizeService.submitAuthorize(principal.getName(), clientId, scope);
        final String code = authorizeService.createCode(clientId, principal.getName(), scope);
        return "redirect:" + redirectURI + "?code=" + code;
    }
}
