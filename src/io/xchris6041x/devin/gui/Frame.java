package io.xchris6041x.devin.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Manages controls and containers that the GUI will have when shown.
 * 
 * @author Christopher Bishop
 */
public class Frame extends PageableContainer {
	
	private String title;
	private int rows;
	
	public Frame(String title, int rows) {
		if(rows < 1) throw new IllegalArgumentException("GUI's cannot have less than 1 row.");
		if(rows > Container.MAX_HEIGHT) throw new IllegalArgumentException("GUI's cannot have more than " + Container.MAX_HEIGHT + " rows.");
		
		this.title = title;
		this.rows = rows;
	}
	
	/**
	 * @return the title of the inventory.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @return the number of rows the inventory will have.
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * Open the GUI to the {@code player}.
	 * @param player
	 */
	public void open(Player player) {
		FrameHolder frameHolder = new FrameHolder(this);
		Inventory inventory = Bukkit.createInventory(frameHolder, rows * 9, title);
		
		render(inventory);
		
		player.openInventory(inventory);
	}
	
	
	@Override
	public void setParent(Container parent) {
		if(parent == null) return;
		throw new UnsupportedOperationException("Cannot set the parent of a Frame.");
	}
	@Override
	public int getWidth() {
		return Container.MAX_WIDTH;
	}
	@Override
	public int getHeight() {
		return Container.MAX_HEIGHT;
	}
	
}
