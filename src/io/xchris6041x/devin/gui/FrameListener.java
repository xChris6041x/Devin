package io.xchris6041x.devin.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class FrameListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getInventory().getHolder() instanceof FrameHolder) {
			e.setCancelled(true); // This will be removed when dragable controls are added.
			
			FrameHolder holder = (FrameHolder) e.getInventory().getHolder();
			holder.getFrame().onClick(holder, e);
		}
	}

}
