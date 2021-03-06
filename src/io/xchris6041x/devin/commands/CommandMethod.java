package io.xchris6041x.devin.commands;

import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;
import org.bukkit.command.CommandSender;

interface CommandMethod {

    /**
     * @return how the command should be used in Command Line Syntax format.
     */
    String getUsage();

    /**
     * @return the argument types, in the order that they are entered.
     */
    Class<?>[] getArgumentTypes();

    /**
     * Execute the CommandMethod.
     *
     * @param sender  - The CommandSender who executed the command.
     * @param rawArgs - The arguments being passed into the method, in string form.
     * @throws DevinException
     */
    void invoke(CommandHandler handler, CommandSender sender, String[] rawArgs, MessageSender msgSender) throws DevinException;

}
