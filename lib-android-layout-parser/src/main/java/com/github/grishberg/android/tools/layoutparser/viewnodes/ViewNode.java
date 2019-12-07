package com.github.grishberg.android.tools.layoutparser.viewnodes;

import com.github.grishberg.layout.parser.Node;
import com.github.grishberg.layout.parser.NodeProperty;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ViewNode implements Node {
	enum ForcedState {
        NONE,
        VISIBLE,
        INVISIBLE
    }
    final Node parent;
    final String name;
    final String hash;
    int index = 0;
    String id;
    List<NodeProperty> properties = Collections.emptyList();
    HashMap<String, List<NodeProperty>> groupedProperties = new HashMap<>();
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
		for (NodeProperty property: properties)
		{
			namedProperties.put(property.fullName(), property);
			addPropertyToGroup(property);
		}
    }

	private void addPropertyToGroup(NodeProperty p) {
		String key = getKey(p);
        List<NodeProperty> propertiesList = groupedProperties.get(key);
		if (propertiesList == null)
		{
            propertiesList = new LinkedList<>();
			groupedProperties.put(key, propertiesList);
        }
        propertiesList.add(p);
	}

	private String getKey(NodeProperty p) {
		String cat = p.category();
		if (cat != null)
		{
			return cat;
		} 
		if (p.fullName().endsWith("()"))
		{
            return "methods";
        } 
        return "properties";
    }
}
