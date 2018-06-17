package boojongmin.oauth2.authorization.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;

public class HashMapBuilder {
    private Map map;

    public HashMapBuilder(Map map) {
        this.map = map;
    }

    public HashMapBuilder add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public Map build() {
        return this.map;
    }

    public String buildJson() throws JsonProcessingException {
        return JsonHelper.serializeJson(this.map);
    }


    public static HashMapBuilder of() {
        return new HashMapBuilder(new HashMap());
    }
}
