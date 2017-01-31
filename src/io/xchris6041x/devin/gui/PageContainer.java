package io.xchris6041x.devin.gui;

public class PageContainer extends RenderableContainer {

	private PageableContainer parent;
	
	public PageContainer(PageableContainer parent) {
		setParent(parent);
	}
	
	/**
	 * @return the parent of this container.
	 */
	public PageableContainer getParent() {
		return parent;
	}
	protected void setParent(PageableContainer parent) {
		this.parent = parent;
	}
	
	@Override
	public int getHeight() {
		return getParent().getHeight();
	}
	
	@Override
	public boolean isValidParent(Container container) {
		return container instanceof PageableContainer;
	}
	
}
