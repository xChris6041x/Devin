package io.xchris6041x.devin.commands;

public final class CommandArg {

	private String name;
	private boolean optional = false;
	
	private CommandArgType type = CommandArgType.STRING;
	
	public CommandArg(String name) {
		this.name = name;
	}
	public CommandArg(String name, boolean optional) {
		this(name);
		this.optional = optional;
	}
	
	public CommandArg(String name, CommandArgType type) {
		this(name);
		this.type = type;
	}
	public CommandArg(String name, CommandArgType type, boolean optional) {
		this(name, type);
		this.optional = optional;
	}
	
}
