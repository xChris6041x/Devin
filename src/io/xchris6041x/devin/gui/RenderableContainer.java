package io.xchris6041x.devin.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.xchris6041x.devin.gui.controls.Control;

public abstract class RenderableContainer extends Container {

	private List<Control> controls = new ArrayList<Control>();
	
	public void addControl(Control control) {
		controls.add(control);
	}
	
	@Override
	public void render(Inventory inv) {
		// Render controls.
		for(Control control : controls) {
			control.render(inv, 0, Container.WIDTH * getHeight());
		}
		// Render children above this container.
		for(Container container : getChildren()) {
			container.render(inv);
		}
	}
	
	@Override
	public boolean onClick(FrameHolder holder, InventoryClickEvent e) {
		for(Container container : getChildren()) {
			if(container.onClick(holder, e)) return true;
		}
		for(Control control : controls) {
			control.click(holder, e, 0);
		}
		
		return false;
	}
	
}
