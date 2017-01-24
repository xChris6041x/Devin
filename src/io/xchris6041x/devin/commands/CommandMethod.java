package io.xchris6041x.devin.commands;

import org.bukkit.command.CommandSender;

import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;

interface CommandMethod {

	/**
	 * @return how the command should be used in Command Line Syntax format.
	 */
	public String getUsage();
	
	/**
	 * Execute the CommandMethod.
	 * @param sender - The CommandSender who executed the command.
	 * @param rawArgs - The arguments being passed into the method, in string form.
	 * @throws DevinException
	 */
	public void invoke(CommandHandler handler, CommandSender sender, String[] rawArgs, MessageSender msgSender) throws DevinException;
	
}
