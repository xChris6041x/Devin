package io.xchris6041x.devin.gui;

import io.xchris6041x.devin.gui.controls.Control;
import io.xchris6041x.devin.gui.controls.ControlManager;

public abstract class PageContainer extends Container {

	private ControlManager controls;
	private PageableContainer parent;
	
	public PageContainer(PageableContainer parent) {
		controls = new ControlManager();
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
	
	
	public ControlManager getControlManager() {
		return controls;
	}
	
	public void addControl(Control control) {
		controls.getControls().add(control);
	}
	
	
	@Override
	public int getHeight() {
		return getParent().getHeight();
	}
	
}
