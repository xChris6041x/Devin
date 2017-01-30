package io.xchris6041x.devin.gui.controls;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import io.xchris6041x.devin.gui.FrameHolder;

public class Button extends Control {

	private OnClickAction action;
	
	public Button(IIconBuilder builder, OnClickAction action) {
		super(builder);
		this.action = action;
	}
	public Button(ItemStack icon, OnClickAction action) {
		super(icon);
		this.action = action;
	}

	@Override
	public boolean onClick(FrameHolder holder, InventoryClickEvent e) {
		return action.onClick(holder, e);
	}

}
