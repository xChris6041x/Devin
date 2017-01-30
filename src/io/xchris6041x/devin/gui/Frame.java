package io.xchris6041x.devin.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Manages controls and containers that the GUI will have when shown.
 * 
 * @author Christopher Bishop
 */
public class Frame implements IContainer<FullContainer> {
	
	private List<FullContainer> containers;
	private String title;
	private int rows;
	
	public Frame(String title, int rows) {
		if(rows > IContainer.MAX_HEIGHT) throw new IllegalArgumentException("Frame's cannot have more than " + IContainer.MAX_HEIGHT + " rows.");
		
		this.containers = new ArrayList<FullContainer>();
		
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

	@Override
	public int getWidth() {
		return IContainer.MAX_WIDTH;
	}

	@Override
	public int getHeight() {
		return IContainer.MAX_HEIGHT;
	}

	@Override
	public List<FullContainer> getContainers() {
		return containers;
	}
	
}
