package io.xchris6041x.devin.playerdata.request;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author Christopher Bishop
 */
public abstract class PlayerRequest {

	private UUID from;
	private UUID to;
	
	public PlayerRequest(Player from, OfflinePlayer to) {
		this.from = from.getUniqueId();
		this.to = to.getUniqueId();
	}
	
	public abstract String getDescription();
	
	
	/**
	 * @return the unique id of
	 */
	public UUID getFromUniqueId() {
		return from;
	}
	
	/**
	 * @return the uinque id of the player who was sent the request.
	 */
	public UUID getToUniqueId() {
		return to;
	}
	
}
