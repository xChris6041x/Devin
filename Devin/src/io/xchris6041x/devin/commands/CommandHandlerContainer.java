package io.xchris6041x.devin.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A class that contains CommandHandlers.
 * @author Christopher Bishop
 */
class CommandHandlerContainer {

	private Map<String[], CommandHandler> handlers;
	
	public CommandHandlerContainer() {
		handlers = new HashMap<String[], CommandHandler>();
	}
	
	
	/**
	 * Add a handler to the container.
	 * @param name - The name of the handler.
	 * @param handler - The handler.
	 */
	public void addHandler(String name, CommandHandler handler) {
		handlers.put(new String[]{ name }, handler);
	}
	
	/**
	 * @param name
	 * @return a CommandHandler with the same name or alias as {@code name}
	 */
	public CommandHandler getHandler(String name) {
		for(Entry<String[], CommandHandler> handler : handlers.entrySet()) {
			for(String str : handler.getKey()) {
				if(name.equalsIgnoreCase(str)) {
					return handler.getValue();
				}
			}
		}
		
		return null;
	}
	
}
