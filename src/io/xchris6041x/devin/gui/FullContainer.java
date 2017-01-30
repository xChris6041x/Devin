package io.xchris6041x.devin.gui;

import java.util.ArrayList;
import java.util.List;

public class FullContainer implements IContainer<IContainer<?>> {

	private List<IContainer<?>> containers;
	
	public FullContainer() {
		containers = new ArrayList<IContainer<?>>();
	}
	
	@Override
	public int getWidth() {
		return IContainer.MAX_WIDTH;
	}

	@Override
	public int getHeight() {
		return IContainer.MAX_HEIGHT;
	}

	@Override
	public List<IContainer<?>> getContainers() {
		return containers;
	}
	
}
