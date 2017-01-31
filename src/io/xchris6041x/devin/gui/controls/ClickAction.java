package io.xchris6041x.devin.gui.controls;

import org.bukkit.event.inventory.InventoryClickEvent;

import io.xchris6041x.devin.gui.FrameHolder;

/**
 * A functional interface for Controls that have variable click
 * functionality.
 * 
 * @author Christopher Bishop
 */
@FunctionalInterface
public interface ClickAction {

	/**
	 * Execute when clicked.
	 * @param holder - The holder of the frame and inventory.
	 * @param e - The event data.
	 * @return whether the click was consumed.
	 */
	public boolean onClick(FrameHolder holder, InventoryClickEvent e);
	
}
