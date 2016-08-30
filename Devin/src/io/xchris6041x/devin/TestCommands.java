package io.xchris6041x.devin;

import org.bukkit.command.CommandSender;

import io.xchris6041x.devin.commands.Command;
import io.xchris6041x.devin.commands.Commandable;

public class TestCommands implements Commandable {

	@Command(struct = "devin")
	public boolean devin(CommandSender sender) {
		Devin.getMessageSender().info("This is a simple command.");
		return true;
	}
	
}
