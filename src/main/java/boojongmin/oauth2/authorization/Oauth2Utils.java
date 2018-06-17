package boojongmin.oauth2.authorization;

import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Oauth2Utils {
    public static String[] parseScopeArray(String scope) {
        final String[] scopeArr = scope.split(",");
        for(int i=0; i < scopeArr.length; i++) {
            scopeArr[i] = StringUtils.trim(scopeArr[i]);
        }
        return scopeArr;
    }

    public static List<String> parseScopeList(String scope) {
        final String[] scopeArr = scope.split(",");
        for(int i=0; i < scopeArr.length; i++) {
            scopeArr[i] = StringUtils.trim(scopeArr[i]);
        }
        return Arrays.asList(scopeArr);
    }
}
