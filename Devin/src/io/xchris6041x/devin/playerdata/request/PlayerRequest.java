package io.xchris6041x.devin.playerdata.request;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author Christopher Bishop
 */
public abstract class PlayerRequest {

	private UUID sender;
	private UUID receiver;
	
	public PlayerRequest(Player sender, OfflinePlayer receiver) {
		this.sender = sender.getUniqueId();
		this.receiver = receiver.getUniqueId();
	}
	
	public abstract String getDescription();
	
	
	/**
	 * @return the unique id of the player who sent the request.
	 */
	public UUID getSenderUniqueId() {
		return sender;
	}
	
	/**
	 * @return the uinque id of the player who received the request.
	 */
	public UUID getReceiverUniqueId() {
		return receiver;
	}
	
}
