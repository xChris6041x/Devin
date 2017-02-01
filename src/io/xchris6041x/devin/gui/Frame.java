package io.xchris6041x.devin.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
	
	/**
	 * {@inheritDoc}
	 * @param title - The title of the Frame.
	 * @param height - How tall the Frame is in slots.
	 */
	public Frame(String title, int height, Material material, short durability) {
		super(material, durability);
		
		if(height < 1) throw new IllegalArgumentException("GUI's height cannot be less than 1.");
		if(height > Container.MAX_HEIGHT) throw new IllegalArgumentException("GUI's height cannot be more than " + Container.MAX_HEIGHT);
		
		this.title = title;
		this.height = height;
	}
	
	/**
	 * {@inheritDoc}
	 * @param title - The title of the Frame.
	 * @param height - How tall the Frame is in slots.
	 */
	public Frame (String title, int height, Material material) {
		this(title, height, material, (short) 0);
	}
	
	/**
	 * {@inheritDoc}
	 * @param title - The title of the Frame.
	 * @param height - How tall the Frame is in slots.
	 */
	public Frame(String title, int height) {
		this(title, height, Material.STAINED_GLASS_PANE, (short) 15);
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
		FrameHolder frameHolder = new FrameHolder(this, player);
		Inventory inventory = Bukkit.createInventory(frameHolder, getSize(), title);
		frameHolder.setInventory(inventory);
		
		render(inventory, player);
		
		player.openInventory(inventory);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHeight() {
		return height;
	}
	
}
