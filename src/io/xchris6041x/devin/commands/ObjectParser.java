package io.xchris6041x.devin.commands;

import io.xchris6041x.devin.DevinException;

/**
 * Converts a string into an object.
 * @author Christopher Bishop
 */
@FunctionalInterface
public interface ObjectParser {

	public Object parseObject(ArgumentStream args) throws DevinException;
	
}
