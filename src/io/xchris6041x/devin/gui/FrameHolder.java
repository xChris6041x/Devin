package io.xchris6041x.devin.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * This is the {@code InventoryHolder} when the frame is open.
 * 
 * @author Christopher Bishop
 */
public class FrameHolder implements InventoryHolder {

	private Frame frame;
	private Inventory inventory;
	
	/**
	 * @param frame - the frame that created the inventory that this holds.
	 */
	public FrameHolder(Frame frame) {
		this.frame = frame;
	}

	/**
	 * Clear the inventory and render the Frame.
	 */
	public void refresh() {
		inventory.clear();
		frame.render(inventory);
	}
	
	/**
	 * @return the frame that created the inventory that this holds.
	 */
	public Frame getFrame() {
		return frame;
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
