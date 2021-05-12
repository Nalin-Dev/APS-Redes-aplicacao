package main.java.programa.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

public class JsonToStringBuilder {
    private final Map<String, Object> json = new HashMap<>();
    private JsonToStringBuilder subObjeto;
    private JsonToStringBuilder parent;
    private String chave;

    public JsonToStringBuilder novaPropriedade(final String chave, final Object valor) {
        if (valor != null) {
            json.put(chave, valor);
        }
        return this;
    }

    public JsonToStringBuilder novoSubObjeto(final String chave) {
        subObjeto = new JsonToStringBuilder();
        subObjeto.setParent(this);
        subObjeto.setChave(chave);
        return subObjeto;
    }

    public String build() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(json);
    }

    public JsonToStringBuilder buildSubObject() {
        parent.json.put(chave, json);
        subObjeto = null;
        return parent;
    }

    private void setParent(JsonToStringBuilder parent) {
        this.parent = parent;
    }

    private void setChave(String chave) {
        this.chave = chave;
    }
}
