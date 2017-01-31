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
	private int height;
	
	public Frame(String title, int height) {
		super();
		
		if(height < 1) throw new IllegalArgumentException("GUI's height cannot be less than 1.");
		if(height > Container.MAX_HEIGHT) throw new IllegalArgumentException("GUI's height cannot be more than " + Container.MAX_HEIGHT);
		
		this.title = title;
		this.height = height;
	}
	
	/**
	 * @return the title of the inventory.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Open the GUI to the {@code player}.
	 * @param player
	 */
	public void open(Player player) {
		FrameHolder frameHolder = new FrameHolder(this);
		Inventory inventory = Bukkit.createInventory(frameHolder, height * Container.WIDTH, title);
		frameHolder.setInventory(inventory);
		
		render(inventory);
		
		player.openInventory(inventory);
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
}
