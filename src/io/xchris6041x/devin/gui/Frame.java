package io.xchris6041x.devin.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Manages controls and containers that the GUI will have when shown.
 * 
 * @author Christopher Bishop
 */
public class Frame {

	public static final int MAX_ROWS = 6;
	
	private String title;
	private int rows;
	
	public Frame(String title, int rows) {
		if(rows > MAX_ROWS) throw new IllegalArgumentException("GUI's cannot have more than " + MAX_ROWS + " rows.");
		
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
		
		player.openInventory(inventory);
	}
	
}
