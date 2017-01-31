package io.xchris6041x.devin.gui.controls;

import org.bukkit.inventory.ItemStack;

/**
 * A {@link IIconBuilder} class that just simply outputs the ItemStack.
 * 
 * @author Christopher Bishop
 */
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
