package io.xchris6041x.devin.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class Container {

	public static final int WIDTH = 9;
	public static final int MAX_HEIGHT = 6;
	
	/**
	 * @return the height of the container.
	 */
	public abstract int getHeight();
	
	/**
	 * Add the controls to the {@code inventory}.
	 * @param inventory
	 */
	public abstract void render(Inventory inventory);
	
	/**
	 * Trigger this container.
	 * @param holder - The holder of the inventory and frame
	 * @param e - The event fired
	 */
	public abstract boolean onClick(FrameHolder holder, InventoryClickEvent e);
	
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
