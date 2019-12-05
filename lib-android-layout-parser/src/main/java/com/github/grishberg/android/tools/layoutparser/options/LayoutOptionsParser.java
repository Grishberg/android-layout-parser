package com.github.grishberg.android.tools.layoutparser.options;

import com.github.grishberg.android.tools.layoutparser.common.ParserLogger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.ObjectInputStream;

public class LayoutOptionsParser {
    private static final String TITLE_KEY = "title";
    private static final String VERSION_KEY = "version";
    private static final String TAG = "LayoutOptionsParser";
    private final ParserLogger log;

    public LayoutOptionsParser(ParserLogger log) {
        this.log = log;
    }

    public LayoutCaptureOptions parse(ObjectInputStream is) throws IOException {
        String json = is.readUTF();
        log.d(TAG, "options: " + json);

        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();

        int version = obj.get(VERSION_KEY).getAsInt();
        String title = obj.get(TITLE_KEY).getAsString();

        return new LayoutCaptureOptions(version, title);
    }
}
