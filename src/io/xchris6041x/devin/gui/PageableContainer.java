package io.xchris6041x.devin.gui;

public class PageableContainer extends Container {
	
	@Override
	public int getWidth() {
		return getParent().getWidth();
	}

	@Override
	public int getHeight() {
		return getParent().getHeight();
	}

	@Override
	public boolean isValidChild(Container container) {
		return container instanceof PageContainer;
	}
	
}
