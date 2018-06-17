package boojongmin.oauth2.authorization;

import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Oauth2Utils {
    public static String[] splitStringToArray(String scope) {
        if(scope == null) return new String[0];
        final String[] scopeArr = scope.split(",");
        for(int i=0; i < scopeArr.length; i++) {
            scopeArr[i] = StringUtils.trim(scopeArr[i]);
        }
        return scopeArr;
    }

    public static List<String> splitStringToList(String scope) {
        if(scope == null) return Collections.emptyList();
        final String[] scopeArr = scope.split(",");
        for(int i=0; i < scopeArr.length; i++) {
            scopeArr[i] = StringUtils.trim(scopeArr[i]);
        }
        return Arrays.asList(scopeArr);
    }
}
