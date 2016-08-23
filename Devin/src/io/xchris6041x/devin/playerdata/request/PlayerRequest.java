package io.xchris6041x.devin.playerdata.request;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

/**
 * @author Christopher Bishop
 */
public abstract class PlayerRequest implements ConfigurationSerializable {

	private UUID sender;
	private UUID receiver;
	
	public PlayerRequest(Player sender, OfflinePlayer receiver) {
		this.sender = sender.getUniqueId();
		this.receiver = receiver.getUniqueId();
	}
	public PlayerRequest(Map<String, Object> map) {
		sender = UUID.fromString((String) map.get("sender"));
		receiver = UUID.fromString((String) map.get("receiver"));
	}
	
	/**
	 * @return the description for the request.
	 */
	public abstract String getDescription();
	
	/**
	 * Send a repsonse to the PlayerRequest.
	 * @param response - Which response was sent to the request.
	 */
	public abstract void respond(String response);
	
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
	
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sender", sender.toString());
		map.put("receiver", receiver.toString());
		
		return map;
	}
	
}
