package io.xchris6041x.devin.gui;

import org.bukkit.entity.Player;
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
	public void render(Inventory inventory, Player player) {
		getControlManager().render(inventory, player, 0, getSize());
		getRelativeControlManager(player).render(inventory, player, 0, getSize());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean click(FrameHolder holder, InventoryClickEvent e) {
		if(getRelativeControlManager(holder.getPlayer()).click(holder, e, 0)) return true;
		return getControlManager().click(holder, e, 0);
	}
	
	/**
	 * @param p
	 * @return a ControlManager that is driven by a player.
	 */
	public abstract ControlManager getRelativeControlManager(Player p);
	
}
