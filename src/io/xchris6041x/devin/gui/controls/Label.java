package io.xchris6041x.devin.gui.controls;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import io.xchris6041x.devin.gui.FrameHolder;

/**
 * A simple control that does not trigger when clicked and must
 * have text.
 * 
 * @author Christopher Bishop
 */
public class Label extends Control {

	public Label(IIconBuilder builder, int pos) {
		super(builder, pos);
	}
	public Label(ItemStack icon, int pos) {
		super(icon, pos);
	}

	@Override
	public boolean onClick(FrameHolder holder, InventoryClickEvent e) {
		return true;
	}
	
}
