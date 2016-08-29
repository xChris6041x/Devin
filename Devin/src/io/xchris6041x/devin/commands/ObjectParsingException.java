package io.xchris6041x.devin.commands;

@SuppressWarnings("serial")
public class ObjectParsingException extends Exception {

	public ObjectParsingException(String message) {
		super(message);
	}
	public ObjectParsingException(String message, Throwable t) {
		super(message, t);
	}
	
}
