package io.xchris6041x.devin.gui.controls;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import io.xchris6041x.devin.gui.FrameHolder;

public class Button extends Control {

	private ClickAction action;
	
	public Button(IIconBuilder builder, int pos, String text, ClickAction action) {
		super(builder, pos, text);
		this.action = action;
	}
	public Button(ItemStack icon, int pos, String text, ClickAction action) {
		super(icon, pos, text);
		this.action = action;
	}

	@Override
	public boolean onClick(FrameHolder holder, InventoryClickEvent e) {
		return action.onClick(holder, e);
	}

}
