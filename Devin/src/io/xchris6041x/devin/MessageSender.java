package io.xchris6041x.devin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * A class for sending messages to players and consoles, with info and error headers.
 * @author Christopher Bishop
 */
public class MessageSender {

	private String headerInfo;
	private String headerWarning;
	private String headerError;
	
	public MessageSender() {
		this("", "", "");
	}
	public MessageSender(String headerInfo, String headerWarning, String headerError) {
		this.headerInfo = headerInfo;
		this.headerWarning = headerWarning;
		this.headerError = headerError;
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
		sender.sendMessage(headerInfo + message);
	}
	
	/**
	 * Send warning message to commands sender.
	 * @param sender - The recipient of the message.
	 * @param message - The message to send.
	 */
	public void warning(CommandSender sender, String message) {
		sender.sendMessage(headerWarning + message);
	}
	
	/**
	 * Send error message to commands sender.
	 * @param sender - The recipient of the message.
	 * @param message - The message to send.
	 */
	public void error(CommandSender sender, String message) {
		sender.sendMessage(headerError + message);
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
		broadcast(headerInfo + message, permission);
	}
	/**
	 * Broadcast a info message.
	 * @param message
	 */
	public void info(String message) {
		broadcast(headerInfo + message);
	}
	
	/**
	 * Broadcast a warning message.
	 * @param message
	 * @param permission
	 */
	public void warning(String message, String permission) {
		broadcast(headerWarning + message, permission);
	}
	/**
	 * Broadcast a warning message.
	 * @param message
	 */
	public void warning(String message) {
		broadcast(headerWarning + message);
	}
	
	/**
	 * Broadcast an error message.
	 * @param message
	 * @param permission
	 */
	public void error(String message, String permission) {
		broadcast(headerError + message, permission);
	}
	/**
	 * Broadcast error message.
	 * @param message
	 */
	public void error(String message) {
		broadcast(headerError + message);
	}
	
}
