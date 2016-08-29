package io.xchris6041x.devin.commands;

import io.xchris6041x.devin.DevinException;

@FunctionalInterface
public interface ObjectParser {

	public Object parseObject(String str) throws DevinException;
	
}
