package io.xchris6041x.devin.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.xchris6041x.devin.gui.controls.Control;

public abstract class RenderableContainer extends Container {

	private Map<Integer, Control> controls = new HashMap<Integer, Control>(); // THIS IS FOR TESTING ONLY
	
	public void addControl(Control control, int pos) {
		if(pos < 0 || pos >= getWidth() * getHeight()) throw new IllegalArgumentException("Cannot place control outside bounds.");
		controls.put(pos, control);
	}
	public void addControl(Control control, int x, int y) {
		addControl(control, y * getWidth() + x);
	}
	
	@Override
	public void render(Inventory inventory) {
		// Render controls.
		for(Entry<Integer, Control> control : controls.entrySet()) {
			inventory.setItem(control.getKey(), control.getValue().getIconBuilder().getIcon());
		}
		// Render children above this container.
		for(Container container : getChildren()) {
			container.render(inventory);
		}
	}
	
	@Override
	public boolean onTrigger(FrameHolder holder, InventoryClickEvent e) {
		for(Container container : getChildren()) {
			if(container.onTrigger(holder, e)) return true;
		}
		for(Entry<Integer, Control> control : controls.entrySet()) {
			if(e.getSlot() == control.getKey()) {
				if(control.getValue().onTrigger(holder, e)) return true;
			}
		}
		
		return false;
	}
	
}
