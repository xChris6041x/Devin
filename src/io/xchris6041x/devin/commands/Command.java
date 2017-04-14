package io.xchris6041x.devin.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotation for telling the CommandRegistrar that this method is a command.
 *
 * @author Christopher Bishop
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * @return The structure of the command with sub commands separated by spaces.
     */
    String struct();

    /**
     * @return The aliases for the command (for the last part of the structure).
     */
    String[] aliases() default {};

    /**
     * @return All the permissions needed to run this command.
     */
    String[] perms() default {};

    /**
     * @return The names of the parameters. This is used when generating the usage message.
     */
    String[] params() default {};

    /**
     * @return The description of this command.
     */
    String desc() default "";

}
