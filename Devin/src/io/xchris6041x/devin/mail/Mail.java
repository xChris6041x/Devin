package io.xchris6041x.devin.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class Mail implements ConfigurationSerializable {

	private UUID sender;
	private UUID receiver;
	private String subject;
	private String message;
	
	public Mail(Player sender, OfflinePlayer receiver, String subject, String message) {
		this.sender = sender.getUniqueId();
		this.receiver = receiver.getUniqueId();
		this.subject = subject;
		this.message = message;
	}
	public Mail(Map<String, Object> map) {
		sender = UUID.fromString((String) map.get("sender"));
		receiver = UUID.fromString((String) map.get("receiver"));
		subject = (String) map.get("subject");
		message = (String) map.get("message");
	}
	
	/**
	 * @return the UUID of the player who sent the message.
	 */
	public UUID getSenderId() {
		return sender;
	}
	/**
	 * @return the sender of the message as an OfflinePlayer.
	 */
	public OfflinePlayer getSender() {
		return Bukkit.getOfflinePlayer(sender);
	}
	
	/**
	 * @return the UUID of the player who received the message.
	 */
	public UUID getReceiverId() {
		return receiver;
	}
	/**
	 * @return the receiver of the message as an OfflinePlayer.
	 */
	public OfflinePlayer getReceiver() {
		return Bukkit.getOfflinePlayer(receiver);
	}
	
	/**
	 * @return the subject.
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * @return the message.
	 */
	public String getMessage() {
		return message;
	}
	
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sender", sender.toString());
		map.put("receiver", receiver.toString());
		map.put("subject", subject);
		map.put("message", message);
		
		return map;
	}
	
}
