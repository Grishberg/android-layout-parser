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
import com.github.grishberg.layout.parser.*;
import java.util.*;

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
        ViewNode root = null;
        ViewNode lastNode = null;
        int lastWhitespaceCount = Integer.MIN_VALUE;
        Stack<ViewNode> stack = new Stack<>();

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
            ViewNode parent = null;
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

    private ViewNode createViewNode(ViewNode parent,
                                String str,
                                Collection<String> skippedProperties) {
        log.d(TAG, "createViewNode name=" + str);
        
        int delimIndex = str.indexOf('@');
        if (delimIndex < 0) {
            throw new IllegalArgumentException("Invalid format for ViewNode, missing @: "+str);
        }
        String name = str.substring(0, delimIndex);
		log.d(TAG, name);
		
        String data = str.substring(delimIndex + 1);
        delimIndex = data.indexOf(' ');
        String hash = data.substring(0, delimIndex);
        ViewNode node = new ViewNode(parent, name, hash);
        node.index = parent == null ? 0 : parent.childCount();
        if (data.length() > delimIndex + 1) {
            node.properties = loadProperties(data.substring(delimIndex + 1), skippedProperties);
//            node.id = node.getProperty("mID", "id").value
        }
//        node.displayInfo = DisplayInfoFactory.createDisplayInfoFromNode(node)
		if(parent != null){
			parent.addChild(node);
		}
        return node;
    }
	
	private List<NodeProperty> loadProperties(String data, 
												Collection<String> skippedProperties){
		ArrayList<NodeProperty> properties = new ArrayList<>();
		int start = 0;
        boolean stop;
        do {
            int index = data.indexOf('=', start);
            String fullName = data.substring(start, index);
            int index2 = data.indexOf(',', index + 1);
            int length = Integer.parseInt(data.substring(index + 1, index2));
            start = index2 + 1 + length;
            if (!skippedProperties.contains(fullName)) {
                String value = data.substring(index2 + 1, index2 + 1 + length);
                ViewNodeProperty property = parseViewProperty(fullName, value);
                //node.namedProperties[property.fullName] = property;
                //node.addPropertyToGroup(property);
            }
            stop = start >= data.length();
            if (!stop) {
                start += 1;
            }
        } while (!stop);
//		properties.sort();
		return properties;
	}
	
	private ViewNodeProperty parseViewProperty(String propertyFullName, String value) {
			int colonIndex = propertyFullName.indexOf(':');
			String category;
			String name;
			if (colonIndex != -1) {
				category = propertyFullName.substring(0, colonIndex);
				name = propertyFullName.substring(colonIndex + 1);
			} else {
				category = null;
				name = propertyFullName;
			}
		log.d(TAG, "propery: \nname = "+propertyFullName +
			  "\ncategory = " + category +
				"\nvalue = " + value);
			return new ViewNodeProperty(propertyFullName, name, category, value);
		
	}
}
