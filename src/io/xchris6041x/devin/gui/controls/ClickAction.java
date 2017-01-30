package io.xchris6041x.devin.gui.controls;

import org.bukkit.event.inventory.InventoryClickEvent;

import io.xchris6041x.devin.gui.FrameHolder;

@FunctionalInterface
public interface ClickAction {

	public boolean onClick(FrameHolder holder, InventoryClickEvent e);
	
}
