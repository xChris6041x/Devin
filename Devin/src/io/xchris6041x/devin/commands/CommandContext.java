package io.xchris6041x.devin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class CommandContext {

	private CommandSender sender;
	private Command cmd;
	private String label;
	private Object[] args;
	
	public CommandContext(CommandSender sender, Command cmd, String label, Object[] args) {
		this.sender = sender;
		this.cmd = cmd;
		this.label = label;
		this.args = args;
	}
	
	
	public CommandSender getSender() {
		return sender;
	}
	public Command getCommand() {
		return cmd;
	}
	public String getLabel() {
		return label;
	}
	
	public Object[] getArgs() {
		return args;
	}
	@SuppressWarnings("unchecked")
	public <T> T getArg(int index) {
		return (T) args[index];
	}
	
}
