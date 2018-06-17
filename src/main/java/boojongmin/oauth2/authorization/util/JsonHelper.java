package boojongmin.oauth2.authorization.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
    private static ObjectMapper mapper = new ObjectMapper();
    public static String serializeJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}
