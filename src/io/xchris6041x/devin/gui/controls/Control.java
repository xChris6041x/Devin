package io.xchris6041x.devin.gui.controls;

import org.bukkit.event.inventory.InventoryClickEvent;

import io.xchris6041x.devin.gui.FrameHolder;

public abstract class Control {

	private IIconBuilder builder;
	
	public Control(IIconBuilder builder) {
		this.builder = builder;
	}
	
	public IIconBuilder getIconBuilder() {
		return builder;
	}
	
	public abstract boolean onClick(FrameHolder holder, InventoryClickEvent e); 
	
}
