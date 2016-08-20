package io.xchris6041x.devin.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;;

@Retention(RetentionPolicy.RUNTIME)
public @interface HelpDescription {

	/**
	 * @return The description of what the command does.
	 */
	public String description();
	
	/**
	 * @return How the command is used without the / and without any labels
	 * Ex. If the command is "/world rename <name>" this would be "<name>"
	 */
	public String usage();
	
}
