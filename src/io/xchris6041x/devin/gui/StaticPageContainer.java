package io.xchris6041x.devin.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * A simple {@link PageContainer} class.
 * 
 * @author Christopher Bishop
 */
public class StaticPageContainer extends PageContainer {

	/**
	 * {@inheritDoc}
	 */
	public StaticPageContainer(PageableContainer parent) {
		super(parent);
	}

	@Override
	public void render(Inventory inventory) {
		getControlManager().render(inventory, 0, Container.WIDTH * getHeight());
	}

	@Override
	public boolean click(FrameHolder holder, InventoryClickEvent e) {
		return getControlManager().click(holder, e, 0);
	}
	
}
