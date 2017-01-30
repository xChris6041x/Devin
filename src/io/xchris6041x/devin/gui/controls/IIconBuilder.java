package io.xchris6041x.devin.gui.controls;

import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface IIconBuilder {

	/**
	 * @return an icon as an ItemStack.
	 */
	public ItemStack getIcon();
	
}
