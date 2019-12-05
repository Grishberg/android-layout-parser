package com.github.grishberg.android.tools.layoutparser.viewnodes;

import com.github.grishberg.android.tools.layoutparser.common.ParserLogger;
import com.github.grishberg.layout.parser.Node;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Stack;

public class ViewNodeV1Parser implements ViewNodeParser {
    private static final String TAG = ViewNodeV1Parser.class.getSimpleName();
    private final ParserLogger log;

    public ViewNodeV1Parser(ParserLogger log) {
        this.log = log;
    }

    @Override
    public Node parse(byte[] bytes, Collection<String> skippedProperties) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(bytes), Charset.forName("UTF-8"))
        );
        Node root = null;
        Node lastNode = null;
        int lastWhitespaceCount = Integer.MIN_VALUE;
        Stack<Node> stack = new Stack<>();

        String line;
        while ((line = reader.readLine()) != null) {
            if ("done.".equals(line.toLowerCase())) {
                break;
            }

            //TODO: check \n in text inside views
            int whitespaceCount = 0;
            while (line.charAt(whitespaceCount) == ' ') {
                whitespaceCount++;
            }
            if (lastWhitespaceCount < whitespaceCount) {
                stack.push(lastNode);
            } else if (!stack.isEmpty()) {
                int count = lastWhitespaceCount - whitespaceCount;
                for (int i = 0; i < count; i++) {
                    stack.pop();
                }
            }
            lastWhitespaceCount = whitespaceCount;
            Node parent = null;
            if (!stack.isEmpty()) {
                parent = stack.peek();
            }
            lastNode = createViewNode(parent, line.trim(), skippedProperties);
            if (root == null) {
                root = lastNode;
            }
        }
        reader.close();
        return null;
    }

    private Node createViewNode(Node parent,
                                String trim,
                                Collection<String> skippedProperties) {
        log.d(TAG, "createViewNode name=" + trim);
        //TODO: implement;
        return null;
    }
}
