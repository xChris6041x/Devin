package io.xchris6041x.devin.gui.controls;

import org.bukkit.inventory.ItemStack;

public class IconBuilder implements IIconBuilder {
	
	private ItemStack icon;
	
	public IconBuilder(ItemStack icon) {
		setIcon(icon);
	}
	
	@Override
	public ItemStack getIcon() {
		return icon.clone();
	}
	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}
	
}
