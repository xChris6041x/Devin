package io.xchris6041x.devin.gui.controls;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import io.xchris6041x.devin.gui.FrameHolder;

public class Label extends Control {

	public Label(IIconBuilder builder) {
		super(builder);
	}
	public Label(ItemStack icon) {
		super(icon);
	}

	@Override
	public boolean onClick(FrameHolder holder, InventoryClickEvent e) {
		return true;
	}
	
}
