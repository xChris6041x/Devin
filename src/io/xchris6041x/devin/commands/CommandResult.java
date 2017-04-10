package io.xchris6041x.devin.commands;

/**
 * Used to return the result of a command, this gives DEVIN more information than just true or false.
 * @author Christopher Bishop
 */
@SuppressWarnings("ALL")
public class CommandResult {
	
	public enum Status {
		SUCCESS, FAILED, USAGE
	}
	
	private Status status;
	
	private String message = null;
	private boolean usePrefix = false;
	
	public CommandResult(Status status, String message, boolean usePrefix) {
		this.status = status;
		this.message = message;
		this.usePrefix = usePrefix;
	}
	
	public Status getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public boolean usePrefix() {
		return usePrefix;
	}
	
	/**
	 * Build a SUCCESS CommandResult.
	 * @param message - The message.
	 * @param usePrefix - Whether to use the message sender's prefix.
	 * @return the built CommandResult.
	 */
	public static CommandResult success(String message, boolean usePrefix) {
		return new CommandResult(Status.SUCCESS, message, usePrefix);
	}
	/**
	 * Build a SUCCESS CommandResult where usePrefix = true.
	 * @param message - The message.
	 * @return the built CommandResult.
	 */
	public static CommandResult success(String message) {
		return success(message, true);
	}
	/**
	 * Build a SUCCESS CommandResult with no message.
	 * @return the built CommandResult.
	 */
	public static CommandResult success() {
		return success(null);
	}
	
	/**
	 * Build a FAILED CommandResult.
	 * @param message - The message.
	 * @param usePrefix - Whether to use the message sender's prefix.
	 * @return the built CommandResult.
	 */
	public static CommandResult failed(String message, boolean usePrefix) {
		return new CommandResult(Status.FAILED, message, usePrefix);
	}
	/**
	 * Build a FAILED CommandResult where usePrefix = true.
	 * @param message - The message.
	 * @return the built CommandResult.
	 */
	public static CommandResult failed(String message) {
		return failed(message, true);
	}
	
	/**
	 * Build a USAGE CommandResult.
	 * @param message - The message.
	 * @param usePrefix - Whether to use the message sender's prefix.
	 * @return the built CommandResult.
	 */
	public static CommandResult usage(String message, boolean usePrefix) {
		return new CommandResult(Status.USAGE, message, usePrefix);
	}
	/**
	 * Build a USAGE CommandResult where usePrefix = true.
	 * @param message - The message.
	 * @return the built CommandResult.
	 */
	public static CommandResult usage(String message) {
		return usage(message, true);
	}
	/**
	 * Build a USAGE CommandResult with no message.
	 * @return the built CommandResult.
	 */
	public static CommandResult usage() {
		return usage(null);
	}
	
}
