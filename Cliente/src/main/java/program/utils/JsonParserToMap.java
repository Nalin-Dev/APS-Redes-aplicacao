package main.java.program.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public final class JsonParserToMap {

    private JsonParserToMap() {
    }

    public static Map<String, Object> parse(final String json) {
        final Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
        try {
            final Gson gson = new Gson();
            return gson.fromJson(json, mapType );
        } catch (Exception e) {
            return new Gson().fromJson("{}", mapType);
        }
    }
}
