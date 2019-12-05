package com.github.grishberg.android.tools.layoutparser;

import com.github.grishberg.android.tools.layoutparser.common.EmptyLogger;
import com.github.grishberg.android.tools.layoutparser.common.ParserLogger;
import com.github.grishberg.android.tools.layoutparser.options.LayoutCaptureOptions;
import com.github.grishberg.android.tools.layoutparser.options.LayoutOptionsParser;
import com.github.grishberg.android.tools.layoutparser.viewnodes.ViewNodeParser;
import com.github.grishberg.android.tools.layoutparser.viewnodes.ViewNodeParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LayoutParser {
    private static final String TAG = LayoutParser.class.getSimpleName();
    private final ParserLogger log;
    private final LayoutOptionsParser optionsParser;
    private final ViewNodeParserFactory parserFactory;
    private final ArrayList<String> skippedProperties;

    public LayoutParser() {
        this(new EmptyLogger());
    }

    public LayoutParser(ParserLogger log) {
        this(log, new LayoutOptionsParser(log),
                new ViewNodeParserFactory(log));
    }

    public LayoutParser(ParserLogger log,
                        LayoutOptionsParser op,
                        ViewNodeParserFactory parserFactory) {
        this.log = log;
        optionsParser = op;
        this.parserFactory = parserFactory;
        skippedProperties = new ArrayList<>();
        skippedProperties.add("bg_");
        skippedProperties.add("fg_");
    }

    public ParsedLayoutHierarchy parse(InputStream is) throws IOException {
        if (is.available() == 0) {
            throw new IOException();
        }
        log.d(TAG, "bytes size=" + is.available());

        ObjectInputStream ois = new ObjectInputStream(is);
        LayoutCaptureOptions options = optionsParser.parse(ois);
        log.d(TAG, "title = " + options.title);
        log.d(TAG, "version = " + options.version);

        byte[] nodeBytes = new byte[ois.readInt()];
        log.d(TAG, "view node size=" + nodeBytes.length);

        ois.readFully(nodeBytes);
        ViewNodeParser nodeParser = parserFactory.createParserForVersion(options.version);
        nodeParser.parse(nodeBytes, skippedProperties);

        return new ParsedLayoutHierarchy(null);
    }
}
