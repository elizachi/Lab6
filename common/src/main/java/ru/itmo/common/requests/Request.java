package ru.itmo.common.requests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Request {
    public final int index;
    public final Object argument;

    public Request(int index, Object argument) {
        this.index = index;
        this.argument = new Gson().toJson(argument);
    }

    public static Request fromJson(String json) {
        return new Gson().fromJson(json, Request.class);
    }


    public <T> T getArgumentAs(Class<T> clazz) {
        return new Gson().fromJson((String) argument, clazz);
    }

    public <T> Object getArgumentAs(TypeToken<T> typeToken) {
        return new Gson().fromJson((String) argument, typeToken.getType());
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
