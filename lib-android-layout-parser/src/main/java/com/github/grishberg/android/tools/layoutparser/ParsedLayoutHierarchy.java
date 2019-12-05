package com.github.grishberg.android.tools.layoutparser;
import com.github.grishberg.layout.parser.*;

public class ParsedLayoutHierarchy
{
	private final Node root;

	public ParsedLayoutHierarchy(Node root) {
		this.root = root;
	}
	
	public Node rootNode() {
		return root;
	}
}
