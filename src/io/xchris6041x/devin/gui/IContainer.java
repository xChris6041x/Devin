package io.xchris6041x.devin.gui;

import java.util.List;

public interface IContainer<T extends IContainer<?>> {
	
	public static final int MAX_WIDTH = 9;
	public static final int MAX_HEIGHT = 6;
	
	/**
	 * @return the width in slots that this container takes up.
	 */
	public int getWidth();
	
	/**
	 * @return the height in slots that this container takes up.
	 */
	public int getHeight();
	
	/**
	 * @return the containers that are contained in this container.
	 */
	public List<T> getContainers();
	
}
