package io.xchris6041x.devin.gui.controls;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.xchris6041x.devin.gui.FrameHolder;

/**
 * A ClickAction that when clicked, it will execute a command as the player who clicked it.
 * 
 * @author Christopher Bishop
 */
public class CommandClickAction implements ClickAction {

	private String command;
	
	/**
	 * @param command - A line of an executable command with arguments.
	 */
	public CommandClickAction(String command) {
		this.command = command;
	}
	
	/**
	 * @return the command that will be executed on click.
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * Set the command that will be executed on click.
	 * @param command - A line of an executable command with arguments.
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	
	@Override
	public boolean onClick(FrameHolder holder, InventoryClickEvent e) {
		Bukkit.getServer().dispatchCommand(holder.getPlayer(), command);
		return true;
	}

}
