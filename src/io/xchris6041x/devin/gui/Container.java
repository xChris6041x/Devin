package io.xchris6041x.devin.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Container's render ItemStacks to the inventory and handle click events from
 * the inventory.
 * 
 * @author Christopher Bishop
 */
public abstract class Container {

	public static final int WIDTH = 9;
	public static final int MAX_HEIGHT = 6;
	
	/**
	 * @return the number of slots.
	 */
	public int getSize() {
		return Container.WIDTH * getHeight();
	}
	
	/**
	 * @return the height of the container.
	 */
	public abstract int getHeight();
	
	/**
	 * Add the controls to the {@code inventory}.
     * @param holder - The holder of the Frame and inventory.
	 * @param inventory - The inventory being rendered on.
	 */
	public abstract void render(FrameHolder holder, Inventory inventory);
	
	/**
	 * Trigger this container.
	 * @param holder - The holder of the inventory and frame
	 * @param e - The event fired
	 */
	public abstract boolean click(FrameHolder holder, InventoryClickEvent e);
	
}
