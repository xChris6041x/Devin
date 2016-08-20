package io.xchris6041x.devin.commands;

import java.util.ArrayList;
import java.util.List;

public final class HelpCommandBuilder {

	/**
	 * Build a help command with specific help text.
	 * @param help - Help text.
	 * @return The help command built by this method.
	 */
	public static HelpCommand build(final String[] help) {
		return new HelpCommand(help);
	}
	
	/**
	 * Build a help command automatically by giving it the LayeredCommandExecutor and
	 * using the HelpDescription annotation.
	 * @param lce - LayeredCommandExecutor.
	 * @return The help command built by this method.
	 */
	public static HelpCommand build(LayeredCommandExecutor lce) {
		List<String> help = new ArrayList<String>();
		
		
		return new HelpCommand(help.toArray(new String[0]));
	}
	
}
