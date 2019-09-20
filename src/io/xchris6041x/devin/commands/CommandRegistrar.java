package io.xchris6041x.devin.commands;

import io.xchris6041x.devin.AnsiColor;
import io.xchris6041x.devin.Devin;
import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;
import io.xchris6041x.devin.injection.InjectedObject;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * The main class responsible for registering command methods.
 *
 * @author Christopher Bishop
 */
public class CommandRegistrar extends CommandHandlerContainer {

    private JavaPlugin plugin;

    public CommandRegistrar(JavaPlugin plugin, MessageSender msgSender) {
        super(null, msgSender);
        this.plugin = plugin;
    }

    /**
     * Register all command methods so they can be ran when the command is used.
     *
     * @param commandable - The class to get all the command methods from.
     * @param msgSender   - A MessageSender for these commands only.
     */
    public void registerCommands(Commandable commandable, MessageSender msgSender) {
        Devin.debug("Registering " + commandable.getClass().getCanonicalName() + ": ");
        Devin.debugHr();

        // Inject objects.
        Devin.getInjector(plugin).inject(commandable, new InjectedObject(msgSender));

        Devin.debug("Looking for @Command...");
        for (Method method : commandable.getClass().getMethods()) {
            Command cmd = method.getAnnotation(Command.class);
            if (cmd == null) continue;
            Devin.debug("\tAttempting to register " + AnsiColor.CYAN + method.getName() + AnsiColor.RESET + ":");

            try {
                AnnotatedCommandMethod commandMethod = AnnotatedCommandMethod.build(commandable, method);
                Command command = commandMethod.getCommandAnnotation();
                String[] struct = command.struct().split(" ");

                CommandHandler handler = getHandler(struct);
                handler.setMessageSender(msgSender);
                handler.setAliases(Arrays.copyOf(command.aliases(), command.aliases().length));
                handler.setDescription(command.desc());
                handler.setMethod(commandMethod);

                // Register permissions
                for (String perm : cmd.perms()) {
                	if(Bukkit.getPluginManager().getPermission(perm) == null) {
	                    Bukkit.getPluginManager().addPermission(new Permission(perm));
	                    Devin.debug("\t\tRegistered permission " + AnsiColor.CYAN + perm + AnsiColor.RESET + " /w Spigot");
                	}
                	else {
                		Devin.debug("\t\tPermission " + AnsiColor.CYAN + perm + AnsiColor.RESET + " already registered /w Spigot");
                	}
                }
                // Register Command
                CommandHandler root = registerCommand(handler);
                if (root != null)
                    Devin.debug("\t\tRegistered command " + AnsiColor.CYAN + root.getName() + AnsiColor.RESET + " /w Spigot");

                Devin.debug(AnsiColor.GREEN + "\t\tSUCCESS" + AnsiColor.RESET);
            } catch (DevinException e) {
                Devin.debug(AnsiColor.RED + "\t\tFAILED: " + e.getMessage() + AnsiColor.RESET);
            }
        }

        Devin.debug();
    }

    /**
     * Register all command methods so they can be ran when the command is used.
     *
     * @param commandable - The class to get all the command methods from.
     */
    public void registerCommands(Commandable commandable) {
        registerCommands(commandable, getMessageSender());
    }

    /**
     * Register a help command for {@code name}'s command.
     *
     * @param name      - The label for the root command.
     * @param helpName  - The name of the sub command for help.
     * @param msgSender - The MessageSender this command will use.
     */
    public void registerHelpCommand(String name, String helpName, MessageSender msgSender) {
        Devin.debug("Registering Help Command For: /" + name);
        Devin.debugHr();

        CommandHandler root = getHandler(name);
        if (root == null) {
            Devin.debug(AnsiColor.RED + "\tFailed: Cannot find command. Is it registered?" + AnsiColor.RESET);
            return;
        }

        CommandHandler handler = getHandler(new String[]{root.getName(), helpName});
        handler.setMessageSender(msgSender);
        handler.setMethod(new HelpCommandMethod(root, helpName));

        Devin.debug(AnsiColor.GREEN + "\tSUCCESS" + AnsiColor.RESET);
        Devin.debug();
    }

    /**
     * Register a help command for {@code name}'s command.
     *
     * @param name     - The label for the root command.
     * @param helpName - The name of the sub command for help..
     */
    public void registerHelpCommand(String name, String helpName) {
        registerHelpCommand(name, helpName, getMessageSender());
    }

    /**
     * Register all the help commands for currently registered commands.
     *
     * @param helpName - The name of the help command.
     */
    public void registerHelpCommands(String helpName) {
        for (CommandHandlerContainer child : getChildren()) {
            registerHelpCommand(child.getName(), helpName);
        }
    }

    /**
     * Register all the help commands for currently registered commands.
     */
    public void registerHelpCommands() {
        registerHelpCommands("help");
    }

    private CommandHandler registerCommand(CommandHandler handler) throws DevinException {
        // Find the root handler.
        CommandHandler root = handler;
        boolean isRoot = true;
        boolean wasRegistered = false;

        while (root.getParent() != this) {
            root = (CommandHandler) root.getParent();
            if (isRoot) isRoot = false;
        }

        // Double check for registered command.
        PluginCommand cmd = plugin.getCommand(root.getName());
        if (cmd == null) {
            try {
                // Construct plugin command.
                Constructor<PluginCommand> con = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
                con.setAccessible(true);

                cmd = con.newInstance(root.getName(), plugin);
                cmd.setLabel(root.getName());
                cmd.setExecutor(root);
                cmd.setTabCompleter(root);

                CommandMap commandMap = getCommandMap();

                commandMap.register(root.getName(), cmd);

                wasRegistered = true;
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new DevinException(e.getClass().getSimpleName() + " - " + e.getMessage(), e);
            }
        } else {
            if (cmd.getPlugin() != plugin)
                throw new DevinException("Your plugin does not own \"" + root.getName() + "\"");
        }

        if (isRoot) {
            CommandMap commandMap = getCommandMap();
            cmd.unregister(commandMap);

            cmd.setAliases(Arrays.asList(root.getAliases()));
            cmd.setUsage(handler.getMethod().getUsage());
            cmd.setDescription(handler.getDescription());

            commandMap.register(root.getName(), cmd);
        }
        return (wasRegistered) ? root : null;
    }

    private CommandHandler getHandler(String[] structure) {
        return getHandler(this, structure, 0);
    }

    private CommandHandler getHandler(CommandHandlerContainer container, String[] structure, int offset) {
        if (offset == structure.length - 1) {
            return container.getHandler(structure[offset], true);
        } else {
            return getHandler(container.getHandler(structure[offset], true), structure, offset + 1);
        }
    }

    private CommandMap getCommandMap() throws DevinException {
        // Register Command
        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            commandMap.setAccessible(true);
            CommandMap map = ((CommandMap) commandMap.get(Bukkit.getServer()));
            commandMap.setAccessible(false);

            return map;
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new DevinException(e.getClass().getSimpleName() + " - " + e.getMessage(), e);
        }
    }

}
