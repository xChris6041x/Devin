package io.xchris6041x.devin.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * This is the {@code InventoryHolder} when the frame is open.
 * 
 * @author Christopher Bishop
 */
public class FrameHolder implements InventoryHolder {

	private Frame frame;
	private Player p;
	
	private Inventory inventory;
	
	/**
	 * @param frame - the frame that created the inventory that this holds.
	 */
	public FrameHolder(Frame frame, Player player) {
		this.frame = frame;
		this.p = player;
	}

	/**
	 * Clear the inventory and render the Frame.
	 */
	public void refresh() {
		inventory.clear();
		frame.render(inventory, p);
	}
	
	/**
	 * @return the frame that created the inventory that this holds.
	 */
	public Frame getFrame() {
		return frame;
	}
	
	/**
	 * @return the player that opened the frame.
	 */
	public Player getPlayer() {
		return p;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Inventory getInventory() {
		return inventory;
	}
	protected void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
}
