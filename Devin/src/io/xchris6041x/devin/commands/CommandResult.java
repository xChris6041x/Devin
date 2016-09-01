package io.xchris6041x.devin.commands;

/**
 * Used to return the result of a command, this gives DEVIN more information than just true or false.
 * @author Christopher Bishop
 */
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
	public CommandResult(Status status, String message) {
		this(status, message, true);
	}
	public CommandResult(Status status) {
		this(status, null);
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
	
}
