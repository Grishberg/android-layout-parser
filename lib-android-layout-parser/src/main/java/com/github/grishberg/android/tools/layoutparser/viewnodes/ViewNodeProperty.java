package com.github.grishberg.android.tools.layoutparser.viewnodes;
import com.github.grishberg.layout.parser.*;

public class ViewNodeProperty implements NodeProperty {
	final String propertyFullName;
	final String name;
	final String category;
	final String value;

	public ViewNodeProperty(String propertyFullName, String name, String category, String value) {
		this.propertyFullName = propertyFullName;
		this.name = name;
		this.category = category;
		this.value = value;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String category() {
		return category;
	}

	@Override
	public String value() {
		return value;
	}

	@Override
	public String fullName() {
		return propertyFullName;
	}

}
