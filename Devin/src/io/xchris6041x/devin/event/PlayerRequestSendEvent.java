package io.xchris6041x.devin.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import io.xchris6041x.devin.playerdata.PlayerDataManager;
import io.xchris6041x.devin.playerdata.request.PlayerRequest;

public class PlayerRequestSendEvent extends PlayerRequestEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	private boolean cancelled = false;
	
	public PlayerRequestSendEvent(PlayerDataManager dataManager, PlayerRequest request) {
		super(dataManager, request);
	}
	
	
	/**
	 * @return the player who sent the request.
	 */
	public Player getSender() {
		return Bukkit.getPlayer(getRequest().getSenderUniqueId());
	}
	
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}


	@Override
	public boolean isCancelled() {
		return cancelled;
	}


	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
}
