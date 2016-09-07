package io.xchris6041x.devin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * A class for sending messages to players and consoles, with info and error headers.
 * @author Christopher Bishop
 */
public class MessageSender {

	private String infoPrefix;
	private String errorPrefix;
	
	public MessageSender() {
		this("", "");
	}
	public MessageSender(String infoPrefix, String errorPrefix) {
		this.infoPrefix = (infoPrefix == null) ? "" : infoPrefix;
		this.errorPrefix = (errorPrefix == null) ? this.infoPrefix : errorPrefix;
	}
	
	/**
	 * @return the info prefix for this MessageSender.
	 */
	public String getInfoPrefix() {
		return infoPrefix;
	}
	/**
	 * @return the error prefix for this MessageSender.
	 */
	public String getErrorPrefix() {
		return errorPrefix;
	}
	
	
	/**
	 * Send messages to the command sender.
	 * @param sender - The recipient of the message.
	 * @param messages - The messages to send.
	 */
	public void send(CommandSender sender, String... messages) {
		for(String message : messages) {
			sender.sendMessage(message);
		}
	}
	
	/**
	 * Send info messages to commands sender.
	 * @param sender - The recipient of the message.
	 * @param messages - The messages to send.
	 */
	public void info(CommandSender sender, String... messages) {
		for(String message : messages) {
			send(sender, infoPrefix + message);
		}
	}
	
	/**
	 * Send error messages to commands sender.
	 * @param sender - The recipient of the message.
	 * @param messages - The messages to send.
	 */
	public void error(CommandSender sender, String... messages) {
		for(String message : messages) {
			send(sender, errorPrefix + message);
		}
	}
	
	/**
	 * Broadcast messages to CommandSenders with the permission.
	 * @param message
	 * @param permission
	 */
	public void broadcast(String message, String permission) {
		Bukkit.broadcast(message, permission);
	}
	/**
	 * Broadcast a message.
	 * @param message
	 */
	public void broadcast(String message) {
		Bukkit.broadcastMessage(message);
	}
	
	/**
	 * Broadcast a info message.
	 * @param message
	 * @param permission
	 */
	public void info(String message, String permission) {
		broadcast(infoPrefix + message, permission);
	}
	/**
	 * Broadcast a info message.
	 * @param message
	 */
	public void info(String message) {
		broadcast(infoPrefix + message);
	}
	
	/**
	 * Broadcast an error message.
	 * @param message
	 * @param permission
	 */
	public void error(String message, String permission) {
		broadcast(errorPrefix + message, permission);
	}
	/**
	 * Broadcast error message.
	 * @param message
	 */
	public void error(String message) {
		broadcast(errorPrefix + message);
	}
	
}
