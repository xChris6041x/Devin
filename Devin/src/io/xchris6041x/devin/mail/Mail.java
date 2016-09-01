package io.xchris6041x.devin.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Mail implements ConfigurationSerializable {

	private UUID sender;
	private UUID receiver;
	private String subject;
	private String message;
	
	private boolean read = false;
	
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
		read = (boolean) map.get("read");
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
	
	/**
	 * @return whether this mail was read or not.
	 */
	public boolean wasRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	
	@Override
	public String toString() {
		return  subject + " - " + getSender().getName();
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sender", sender.toString());
		map.put("receiver", receiver.toString());
		map.put("subject", subject);
		map.put("message", message);
		map.put("read", read);
		
		return map;
	}
	
}
