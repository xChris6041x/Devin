package io.xchris6041x.devin.utils;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	private ItemStack stack;
	private ItemMeta meta;
	
	public ItemBuilder(Material type) {
		this.stack = new ItemStack(type);
		this.meta = stack.getItemMeta();
	}
	
	public ItemStack get() {
		stack.setItemMeta(meta);
		return stack;
	}
	public ItemMeta getMeta() {
		return meta;
	}
	
	
	public ItemBuilder setDurability(short durability) {
		stack.setDurability(durability);
		return this;
	}
	public ItemBuilder setDisplayName(String name) {
		meta.setDisplayName(name);
		return this;
	}
	public ItemBuilder setLore(String... lore) {
		meta.setLore(Arrays.asList(lore));
		return this;
	}
	
}
