package io.xchris6041x.devin.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.xchris6041x.devin.MessageSender;

/**
 * A class that contains CommandHandlers.
 * @author Christopher Bishop
 */
class CommandHandlerContainer {

	private MessageSender msgSender;
	private Map<List<String>, CommandHandler> handlers;
	
	public CommandHandlerContainer(MessageSender msgSender) {
		this.msgSender = msgSender;
		handlers = new HashMap<List<String>, CommandHandler>();
	}
	
	
	public MessageSender getMessageSender() {
		return msgSender;
	}
	
	
	/**
	 * Add a handler to the container.
	 * @param name - The name of the handler.
	 * @param handler - The handler.
	 */
	public void addHandler(String name, CommandHandler handler) {
		handlers.put(Arrays.asList(name), handler);
	}
	
	/**
	 * Add a handler to the container with aliases.
	 * @param name - The name of the handler.
	 * @param handler - The handler.
	 */
	public void addHandler(String name, String[] aliases, CommandHandler handler) {
		List<String> ids = Arrays.asList(aliases);
		ids.add(name);
		
		handlers.put(ids, handler);
	}
	
	/**
	 * Add alises to a CommandHandler.
	 * @param name
	 * @param aliases
	 */
	public void addAliases(String name, String[] aliases) {
		for(Entry<List<String>, CommandHandler> handler : handlers.entrySet()) {
			for(String str : handler.getKey()) {
				if(name.equalsIgnoreCase(str)) {
					handler.getKey().addAll(Arrays.asList(aliases));
					return;
				}
			}
		}
		
		CommandHandler handler = new CommandHandler(msgSender);
		addHandler(name, aliases, handler);
	}
	
	
	public CommandHandler getHandler(String name) {
		return getHandler(name, false);
	}
	
	/**
	 * @param name
	 * @return a CommandHandler with the same name or alias as {@code name}
	 */
	public CommandHandler getHandler(String name, boolean create) {
		for(Entry<List<String>, CommandHandler> handler : handlers.entrySet()) {
			for(String str : handler.getKey()) {
				if(name.equalsIgnoreCase(str)) {
					return handler.getValue();
				}
			}
		}
		
		if(create) {
			CommandHandler handler = new CommandHandler(msgSender);
			addHandler(name, handler);
			
			return handler;
		}
		else {
			return null;
		}
	}
	
}
