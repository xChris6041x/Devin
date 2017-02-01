package io.xchris6041x.devin.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.xchris6041x.devin.gui.controls.ControlManager;

/**
 * A {@link PageContainer} where it's contents are driven by a player.
 * 
 * @author Christopher Bishop
 */
public abstract class PlayerPageContainer extends PageContainer {

	/**
	 * {@inheritDoc}
	 */
	public PlayerPageContainer(PageableContainer parent) {
		super(parent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(FrameHolder holder, Inventory inventory) {
		getControlManager().render(holder, inventory, 0, getSize());
		getRelativeControlManager(holder).render(holder, inventory, 0, getSize());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean click(FrameHolder holder, InventoryClickEvent e) {
		if(getRelativeControlManager(holder).click(holder, e, 0)) return true;
		return getControlManager().click(holder, e, 0);
	}
	
	/**
	 * @param p
	 * @return a ControlManager that is driven by a player.
	 */
	public abstract ControlManager getRelativeControlManager(FrameHolder holder);
	
}
