package com.remainsoftware.ece2013.nebula.parts.stw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XFile {

	private final XFile parent;
	private final String name;
	private final String description;
	private List<XFile> children;
	private final boolean hasChildren;

	public XFile(XFile parent, String name, String description,
			boolean hasChildren) {
		super();
		this.parent = parent;
		this.name = name;
		this.description = description;
		this.hasChildren = hasChildren;
	}

	public String getDescription() {
		return description;
	}

	public XFile getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public List<XFile> getChildren() {

		String[] names = new String[] { "", "workspace", "images", "documents",
				"files", "pictures", "movies" };

		if (children == null) {
			children = new ArrayList<>();

			if (hasChildren) {
				for (int i = 1; i < new Random().nextInt(50) + 2; i++) {
					String name = names[new Random().nextInt(5) + 1];
					String parentName = parent == null ? "" : parent.getName() +"/";
					children.add(new XFile(this, parentName + name,
							"this is a " + name + " subfolder", true));
				}
				for (int i = 1; i < new Random().nextInt(15) + 1; i++) {
					children.add(new XFile(this, "file " + i, "this is file "
							+ i, false));
				}
			}
		}
		return children;
	}
}
