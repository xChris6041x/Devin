package io.xchris6041x.devin.gui.controls;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.xchris6041x.devin.gui.FrameHolder;

public abstract class Control {

	private Material material;
	private short durability;
	
	private int amount;
	
	private List<String> lore;
	
	
	public ItemStack buildIcon() {
		ItemStack icon = new ItemStack(material, amount);
		icon.setDurability(durability);
		
		ItemMeta meta = icon.getItemMeta();
		meta.setLore(lore);
		icon.setItemMeta(meta);
		
		return icon;
	}
	
	public abstract boolean onTrigger(FrameHolder frameHolder, InventoryClickEvent e); 
	
}
