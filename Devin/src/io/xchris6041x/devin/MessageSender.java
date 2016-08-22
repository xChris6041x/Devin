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
	 * Send message to the command sender.
	 * @param sender
	 * @param message
	 */
	public void send(CommandSender sender, String message) {
		sender.sendMessage(message);
	}
	
	/**
	 * Send info message to command sender.
	 * @param sender - The recipient of the message.
	 * @param message - The message to send.
	 */
	public void info(CommandSender sender, String message) {
		sender.sendMessage(infoPrefix + message);
	}
	
	/**
	 * Send error message to commands sender.
	 * @param sender - The recipient of the message.
	 * @param message - The message to send.
	 */
	public void error(CommandSender sender, String message) {
		sender.sendMessage(errorPrefix + message);
	}
	
	/**
	 * Broadcast a message.
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
