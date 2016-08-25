package io.xchris6041x.devin.commands;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CommandArgs.class)
public @interface CommandArg {
	
	public String name();
	public boolean optional() default false;
	
	public CommandArgType type() default CommandArgType.STRING;
	
}
