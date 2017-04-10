package io.xchris6041x.devin.gui.controls;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A FunctionalInterface builds icons for Controls.
 * 
 * @author Christopher Bishop
 */
@FunctionalInterface
public interface IIconBuilder {

	/**
	 * @return an icon as an ItemStack.
	 */
    ItemStack getIcon(Player player);
	
}
