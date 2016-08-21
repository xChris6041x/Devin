package io.xchris6041x.devin.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandOptions {

	/**
	 * @return The description of what the command does.
	 */
	public String description() default "";
	
	/**
	 * @return The command paramters in <required> [optional] format.
	 */
	public String parameters() default "";
	
	/**
	 * @return Whether this command can only be executed by players.
	 */
	public boolean onlyPlayers() default false;
	
	/**
	 * @return Whether this command can only be executed by players with the permission.
	 */
	public String permission() default "[NULL]";
	
	/**
	 * @return Whether this command can only be executed by ops.
	 */
	public boolean onlyOps() default false;
	
}
