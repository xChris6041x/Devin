package io.xchris6041x.devin.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import io.xchris6041x.devin.MessageSender;

/**
 * A class that contains CommandHandlers.
 * @author Christopher Bishop
 */
class CommandHandlerContainer implements TabCompleter {
	
	private String name;
	private String[] aliases = new String[0];
	private String description = "";
	
	private MessageSender msgSender;
	
	private CommandHandlerContainer parent = null;
	private List<CommandHandlerContainer> children = new ArrayList<>();
	
	public CommandHandlerContainer(String name, MessageSender msgSender) {
		this.name = name;
		this.msgSender = msgSender;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String[] getAliases() {
		return aliases;
	}
	public void setAliases(String[] aliases) {
		this.aliases = aliases;
	}
	
	/**
	 * @param name - The name to check.
	 * @return whether {@code name} matches any valid name or alias.
	 */
	public boolean isValidName(String name) {
		if(name.equals(this.name)) {
			return true;
		}
		
		for(String str : this.aliases) {
			if(name.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public MessageSender getMessageSender() {
		return msgSender;
	}
	public void setMessageSender(MessageSender msgSender) {
		this.msgSender = msgSender;
	}
	
	
	public CommandHandlerContainer getParent() {
		return parent;
	}
	public void setParent(CommandHandlerContainer parent) {
		if(this.parent != null) {
			this.parent.children.remove(this);
		}
		if(parent != null) {
			parent.children.add(this);
		}
		
		this.parent = parent;
	}
	
	/**
	 * @return all the children in this container.
	 */
	public CommandHandlerContainer[] getChildren() {
		return children.toArray(new CommandHandlerContainer[0]);
	}
	
	/**
	 * Get child with the name or alias of {@code name}
	 * @param name
	 * @return
	 */
	public CommandHandler getHandler(String name) {
		return getHandler(name, false);
	}
	
	/**
	 * @param name
	 * @return a CommandHandler with the same name or alias as {@code name}
	 */
	public CommandHandler getHandler(String name, boolean create) {
		for(CommandHandlerContainer handler : children) {
			if(!(handler instanceof CommandHandler)) continue;
			if(handler.isValidName(name)) return (CommandHandler) handler;
		}
		
		if(create) {
			CommandHandler handler = new CommandHandler(name, msgSender);
			handler.setParent(this);
			
			return handler;
		}
		else {
			return null;
		}
	}


	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        //
        // This method tab completes for sub-commands, then parameters.
        //
		List<String> completions = new ArrayList<>();
		if(args.length > 1) {
			String sub = args[0];
			for(CommandHandlerContainer child : children) {
				if(child.isValidName(args[0])) {
                    // Remove first arg.
                    String[] newArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, newArgs, 0, args.length - 1);

                    return child.onTabComplete(sender, cmd, label + " " + sub, newArgs);
				}
			}
		}
		else {
			List<String> commands = new ArrayList<>();
			for (CommandHandlerContainer child : children) {
				commands.add(child.getName());
			}
			StringUtil.copyPartialMatches(args[0], commands, completions);
		}
		return completions;
	}

}
