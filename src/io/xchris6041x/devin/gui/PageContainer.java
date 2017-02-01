package io.xchris6041x.devin.gui;

import io.xchris6041x.devin.gui.controls.Control;
import io.xchris6041x.devin.gui.controls.ControlManager;

/**
 * An abstract class that is a child of {@link PageableContainer}.
 * This renders the {@link Control}s to the inventory and triggers
 * their click events when this is the current page. 
 * 
 * @author Christopher Bishop
 */
public abstract class PageContainer extends Container {

	private ControlManager controls;
	private PageableContainer parent;
	
	/**
	 * Create a PageContainer with a parent of {@code parent}.
	 * @param parent
	 */
	public PageContainer(PageableContainer parent) {
		controls = new ControlManager();
		setParent(parent);
	}
	
	/**
	 * @return the parent of this container.
	 */
	public final PageableContainer getParent() {
		return parent;
	}
	
	/**
	 * Set the parent of this container. This will automatically add and remove
	 * this page from the new and old parent respectively.
	 * @param parent
	 */
	public final void setParent(PageableContainer parent) {
		if(parent == null) throw new IllegalArgumentException("PageContainers must have a parent.");
		if(this.parent != null) this.parent.getPages().remove(this);
		
		this.parent = parent;
		this.parent.getPages().add(this);
	}
	
	/**
	 * @return this page's control manager.
	 */
	public ControlManager getControlManager() {
		return controls;
	}
	
	/**
	 * Add a control to this page.
	 * @param control - The control to be added.
	 */
	public void addControl(Control control) {
		controls.getControls().add(control);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHeight() {
		return getParent().getHeight();
	}
	
}
