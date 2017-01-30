package io.xchris6041x.devin.gui;

import java.util.ArrayList;
import java.util.List;

public abstract class Container {

	public static final int MAX_WIDTH = 9;
	public static final int MAX_HEIGHT = 6;
	
	private Container parent;
	private List<Container> children;
	
	public Container(Container parent) {
		this.children = new ArrayList<Container>();
		this.setParent(parent);
	}
	public Container() {
		this(null);
	}
	
	/**
	 * @return the parent of this container.
	 */
	public Container getParent() {
		return parent;
	}
	
	/**
	 * Sets this containers parent to {@code parent}.
	 * @param parent
	 */
	public void setParent(Container parent) {
		if(parent == null) {
			if(this.parent != null) this.parent.children.remove(this);
			this.parent = null;
		}
		else {
			if(!this.isValidParent(parent)) throw new IllegalArgumentException("Illegal parent.");
			if(!parent.isValidChild(this)) throw new IllegalArgumentException("Illegal Child");
			
			if(this.parent != null) this.parent.children.remove(this);
			this.parent = parent;
			if(this.parent != null) this.parent.children.add(this);
		}
	}
	
	
	/**
	 * @return the width of the container.
	 */
	public abstract int getWidth();
	
	/**
	 * @return the height of the container.
	 */
	public abstract int getHeight();
	
	/**
	 * @param container
	 * @return whether the {@code container} is suitable to be a parent of this container.
	 */
	public boolean isValidParent(Container container) {
		return true;
	}
	
	/**
	 * @param container
	 * @return whether the {@code container} is suitable to be a child of this container.
	 */
	public boolean isValidChild(Container container) {
		return true;
	}
	
}
