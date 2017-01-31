package io.xchris6041x.devin.gui;

import org.bukkit.entity.Player;
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
	 * @param inventory
	 * @param player - The player who rendered the container.
	 */
	public abstract void render(Inventory inventory, Player player);
	
	/**
	 * Trigger this container.
	 * @param holder - The holder of the inventory and frame
	 * @param e - The event fired
	 */
	public abstract boolean click(FrameHolder holder, InventoryClickEvent e);
	
}
