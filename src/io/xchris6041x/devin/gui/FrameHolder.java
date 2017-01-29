package io.xchris6041x.devin.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

class FrameHolder implements InventoryHolder {

	private Frame frame;
	private Inventory inventory;
	
	public FrameHolder(Frame frame) {
		this.frame = frame;
	}
	
	public Frame getFrame() {
		return frame;
	}
	
	@Override
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
}
