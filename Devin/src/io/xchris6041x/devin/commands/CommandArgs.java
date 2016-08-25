package io.xchris6041x.devin.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandArgs {

	public CommandArg[] value();
	
}
