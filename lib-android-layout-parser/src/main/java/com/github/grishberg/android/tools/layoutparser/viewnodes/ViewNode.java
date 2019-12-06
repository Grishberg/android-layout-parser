package com.github.grishberg.android.tools.layoutparser.viewnodes;

import com.github.grishberg.layout.parser.Node;
import com.github.grishberg.layout.parser.NodeProperty;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ViewNode implements Node {
    final Node parent;
    final String name;
    final String hash;
    int index = 0;
    String id;
    List<NodeProperty> properties = Collections.emptyList();
    HashMap<String, NodeProperty> groupedProperties = new HashMap<>();
    HashMap<String, NodeProperty> namedProperties = new HashMap<>();
    private LinkedList<Node> children = new LinkedList<>();

    public ViewNode(Node parent, String name, String hash) {
        this.parent = parent;
        this.name = name;
        this.hash = hash;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int childCount() {
        // TODO: Implement this method
        return children.size();
    }

    void addChild(ViewNode node) {
        children.add(node);
    }

    @Override
    public Node parent() {
        return parent;
    }

    @Override
    public Node childAt(int p1) {
        return children.get(p1);
    }

    @Override
    public NodeProperty getProperty(String name, String altNames) {
        NodeProperty namedProperty = namedProperties.get(name);
        return namedProperty;
    }

    @Override
    public List<Node> children() {
        return children;
    }

    void setProperties(List<NodeProperty> properties) {

    }
}
