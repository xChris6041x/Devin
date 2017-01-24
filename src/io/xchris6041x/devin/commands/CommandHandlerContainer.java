package io.xchris6041x.devin.commands;

import java.util.ArrayList;
import java.util.List;

import io.xchris6041x.devin.MessageSender;

/**
 * A class that contains CommandHandlers.
 * @author Christopher Bishop
 */
class CommandHandlerContainer {
	
	private String name;
	private String[] aliases = new String[0];
	private String description = "";
	
	private MessageSender msgSender;
	
	private CommandHandlerContainer parent = null;
	private List<CommandHandlerContainer> children = new ArrayList<CommandHandlerContainer>();
	
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
	 * Get child with the name or aliase of {@code name}
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
			if(name.equals(handler.getName())) {
				return (CommandHandler) handler;
			}
			
			for(String str : handler.getAliases()) {
				if(name.equals(str)) {
					return (CommandHandler) handler;
				}
			}
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
	
}
