package com.github.grishberg.android.tools.layoutparser.viewnodes;

import com.github.grishberg.layout.parser.Node;

import java.io.IOException;
import java.util.Collection;

public interface ViewNodeParser {
    Node parse(byte[] bytes, Collection<String> skippedOptions) throws IOException;
}
