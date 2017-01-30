package io.xchris6041x.devin.utils;

import java.util.Arrays;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A class for various functions used with ItemStacks
 * 
 * @author Christopher Bishop
 */
public class ItemStackUtil {

	/**
	 * Set the {@code lore} or the ItemStack {@code stack}.
	 * @param stack
	 * @param lore
	 */
	public static void setLore(ItemStack stack, String... lore) {
		ItemMeta meta = stack.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		stack.setItemMeta(stack.getItemMeta());
	}
	
}
