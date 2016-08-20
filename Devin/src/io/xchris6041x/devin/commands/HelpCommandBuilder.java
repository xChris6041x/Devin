package io.xchris6041x.devin.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.CommandExecutor;

import io.xchris6041x.devin.MessageSender;

/**
 * @author Christopher Bishop
 */
public final class HelpCommandBuilder {

	/**
	 * Build a help command with specific help text.
	 * @param msgSender - The method which messages are sent.
	 * @param help - Help text.
	 * @return The help command built by this method.
	 */
	public static HelpCommand build(MessageSender msgSender, String[] help) {
		return new HelpCommand(msgSender, help);
	}
	
	/**
	 * Build a help command automatically by giving it the LayeredCommandExecutor and
	 * using the HelpDescription annotation.
	 * @param msgSender - The method which messages are sent.
	 * @param lce - LayeredCommandExecutor.
	 * @return The help command built by this method.
	 */
	public static HelpCommand build(MessageSender msgSender, String label, LayeredCommandExecutor lce) {
		List<String> help = new ArrayList<String>();
		buildList(help, label, lce);
		
		return new HelpCommand(msgSender, help.toArray(new String[0]));
	}
	private static void buildList(List<String> help, String label, LayeredCommandExecutor lce) {
		if(lce.getExecutor() != null) {
			Class<? extends CommandExecutor> exClass = lce.getExecutor().getClass();
			HelpDescription hd = exClass.getAnnotation(HelpDescription.class);
			
			help.add("/" + label + hd.usage() + "|" + hd.description());
		}
		
		for(Entry<String, LayeredCommandExecutor> layer : lce.getLayerMap().entrySet()) {
			buildList(help, label + " " + layer.getKey(), layer.getValue());
		}
	}
	
}
