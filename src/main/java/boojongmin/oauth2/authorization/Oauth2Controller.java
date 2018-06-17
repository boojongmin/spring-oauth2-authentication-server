package boojongmin.oauth2.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static boojongmin.oauth2.authorization.Oauth2Utils.parseScopeArray;

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
        authorizeService.checkClient(clientId, scope, redirectURI);
        final String[] scopes = parseScopeArray(scope);
        mv.addObject("scopes", scopes);
        mv.addObject("clientId", clientId);

        mv.addObject("redirect_uri", redirectURI);
        mv.addObject("response_type", responseType);
        mv.setViewName("authorize");
        return mv;
    }

    @PostMapping("/authorize/submit")
    public ModelAndView authorizeSubmit() {
        return null;
    }
}
