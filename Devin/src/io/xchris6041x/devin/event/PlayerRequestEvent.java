package io.xchris6041x.devin.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import io.xchris6041x.devin.playerdata.PlayerDataManager;
import io.xchris6041x.devin.playerdata.request.PlayerRequest;

public abstract class PlayerRequestEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private PlayerDataManager dataManager;
	private PlayerRequest request;
	
	
	public PlayerRequestEvent(PlayerDataManager dataManager, PlayerRequest request) {
		this.dataManager = dataManager;
		this.request = request;
	}
	
	
	/**
	 * @return The PlayerDataManager responsible for sending the request.
	 */
	public PlayerDataManager getPlayerDataManager() {
		return dataManager;
	}
	
	/**
	 * @return The request that was manipulated.
	 */
	public PlayerRequest getRequest() {
		return request;
	}
	
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
