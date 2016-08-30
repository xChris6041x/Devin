package io.xchris6041x.devin.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks fields so that the CommandRegistrar can inject objects.
 * 
 * Supported Data Types:
 *  - MessageSender
 * 
 * @author Christopher Bishop
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DevinInject {
}
