package io.xchris6041x.devin.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.xchris6041x.devin.gui.controls.ControlManager;

/**
 * A {@link PageContainer} where it's contents are driven by a player.
 * 
 * @author Christopher Bishop
 */
public abstract class DynamicPageContainer extends PageContainer {

	/**
	 * {@inheritDoc}
	 */
	public DynamicPageContainer(PageableContainer parent) {
		super(parent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(FrameHolder holder, Inventory inventory) {
		getControlManager().render(holder, inventory, 0, getSize());
		getDynamicControlManager(holder).render(holder, inventory, 0, getSize());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean click(FrameHolder holder, InventoryClickEvent e) {
        return getDynamicControlManager(holder).click(holder, e, 0) || getControlManager().click(holder, e, 0);
    }
	
	/**
	 * @return a ControlManager that is driven by a player.
	 */
	public abstract ControlManager getDynamicControlManager(FrameHolder holder);
	
}
