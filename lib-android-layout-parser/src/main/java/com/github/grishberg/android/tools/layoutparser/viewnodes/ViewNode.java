package com.github.grishberg.android.tools.layoutparser.viewnodes;
import com.github.grishberg.layout.parser.*;
import java.util.*;

public class ViewNode implements Node {
	final Node parent; 
	final String name;
	final String hash;
	int index = 0;
	String id;
	List<NodeProperty> properties = Collections.emptyList();
	private LinkedList<ViewNode> children = new LinkedList<>();

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
	public NodeProperty getProperty(String p1, String p2) {
		// TODO: Implement this method
		return null;
	}

	@Override
	public Enumeration<Node> children() {
		// TODO: Implement this method
		return null;
	}
}
