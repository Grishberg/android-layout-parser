package com.github.grishberg.android.tools.layoutparser.viewnodes;

import com.github.grishberg.android.tools.layoutparser.common.ParserLogger;

public class ViewNodeParserFactory {
    private final ParserLogger log;

    public ViewNodeParserFactory(ParserLogger log) {
        this.log = log;
    }

    public ViewNodeParser createParserForVersion(int version) {
        if (version == 1) {
            return new ViewNodeV1Parser(log);
        }
        return null;
    }
}
