package io.xchris6041x.devin.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class FrameListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getInventory().getHolder() instanceof FrameHolder) {
			FrameHolder holder = (FrameHolder) e.getInventory().getHolder();
			if(e.getRawSlot() < holder.getFrame().getRows() * Container.MAX_WIDTH) {
				e.setCancelled(true); // This will change when draggable controls are added.
				holder.getFrame().onClick(holder, e);
			}
			else {
				e.setCancelled(true);
			}
		}
	}

}
