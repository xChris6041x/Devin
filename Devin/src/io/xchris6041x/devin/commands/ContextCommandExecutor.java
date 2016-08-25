package io.xchris6041x.devin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class ContextCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return onCommand(new CommandContext(sender, cmd, label, args));
	}
	public abstract boolean onCommand(CommandContext ctx);

}
