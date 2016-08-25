package io.xchris6041x.devin.commands;

import io.xchris6041x.devin.Validator;

/**
 * An enum with types of command arguments.
 * @author Christopher Bishop
 */
public enum CommandArgType {
	STRING("String"), INT("Integer"), FLOAT("Number"), DOUBLE("Number"), BOOLEAN("True or false");
	
	private String displayName;
	
	CommandArgType(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * @param str
	 * @return whether the string is this type.
	 */
	public boolean isValid(String str) {
		switch(this) {
		case STRING:
			return true;
		case INT:
			return Validator.isInteger(str);
		case FLOAT:
			return Validator.isFloat(str);
		case DOUBLE:
			return Validator.isDouble(str);
		case BOOLEAN:
			return Validator.isBoolean(str);
		default:
			return false;
		}
	}
}
