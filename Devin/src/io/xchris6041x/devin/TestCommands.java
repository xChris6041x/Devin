package io.xchris6041x.devin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.xchris6041x.devin.commands.Command;
import io.xchris6041x.devin.commands.Commandable;

public class TestCommands implements Commandable {

	@Command(struct = "devin")
	public boolean devin(CommandSender sender, Player player) {
		Devin.getMessageSender().info(player, "This is a simple command.");
		return true;
	}
	
	@Command(struct = "devin error")
	public boolean devinError(CommandSender sender, Player player) {
		Devin.getMessageSender().error(player, "This is an error!");
		return true;
	}
	
}
