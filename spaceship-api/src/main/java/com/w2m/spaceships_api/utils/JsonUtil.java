package com.w2m.spaceships_api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setDateFormat(Constants.DATE_FULL_FORMAT).create();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}