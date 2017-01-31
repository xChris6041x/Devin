package io.xchris6041x.devin.gui.controls;

import org.bukkit.entity.Player;
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
	public ItemStack getIcon(Player player) {
		return icon;
	}
	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}
	
}
