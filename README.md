![DEVIN](http://gdurl.com/yp4P)

DEVIN is a framework for Spigot 1.10+ that gives developers the tools to build better and bigger plugins more efficiently. DEVIN takes care of a lot of the cumbersome code that developers would have to traditionally write so they can spend more time developing a better plugin.

| Features |
| --- |
| `MessageSender` Simplifies sending messages with multi-messages and prefixes. |
| A powerful annotated command framework that can automatically register commands and permissions without `plugin.yml`. |
| `CommandUtils` is a utility class that has common yet complicated tasks such as pagination |
| Linking temporary or persistent data to Players, Worlds, and other objects that use UUID's to identify them. |

## Requirements
- Server is running Java 8.
- Server is running SpigotMC 1.10+

## Start Developing
If you are just learning how to develop with DEVIN then you should read over the [wiki](https://github.com/xChris6041x/Devin/wiki).

### Setting Up
1. Download the latest Devin.jar from the [spigot resource](https://www.spigotmc.org/resources/devin.29241/) and install on the server.
2. Create a new Spigot project.
3. Add the Devin.jar as an external reference.

## Command Framework
Here is a quick overview of what the command framework can do with comments teaching you most the parts of the framework.
```JAVA
// All command classes must implement Commandable
public class DemoCommands implements Commandable {

    @DevinInject
    public MessageSender messageSender; // Grabs the message sender from the registrar.
   
    // All commands must use @Command
    // This command will have an auto generated usage of /demo <message> [message type] [player].
    @Command(
            struct = "demo" /* Command structure (REQUIRED) */,
            params = { "message", "message type", "player" } /* Parameter names */,
            perms = "devin.demo" /* Permissions */,
            aliases = { "d", "dem" } /* Command aliases */ )
    public CommandResult demo(CommandSender sender /* Who sent the command */,
            String str /* First argument. */,
            @OptionalArg("0") int i /* Optional argument. Must be an int and 0 if missing. */,
            @OptionalArg Player p /* Optinal argument, must be a player name (converted to player) and null if missing. */) {
       
        CommandSender cs = (p == null) ? sender : p;
        switch (i) {
        case 1:
            messageSender.info(cs, str);
            break;
        case -1:
            messageSender.error(cs, str);
            break;
        default:
            messageSender.send(cs, str);  
        }
       
        // Tells DEVIN this command was a success!
        return CommandResult.success();
    }
   
    @Command(struct = "demo player-only" /* sub command */, aliases = "po")
    public CommandResult playerOnly(Player sender /* Who sent the command must be a Player */) {
        return CommandResult.success("This is a player only command.");
    }
   
}
```

Without comments:

```JAVA
public class DemoCommands implements Commandable {

    @DevinInject
    public MessageSender messageSender;
   
    @Command(struct = "demo", params = { "message", "message type", "player" }, perms = "devin.demo", aliases = { "d", "dem" })
    public CommandResult demo(CommandSender sender, String str, @OptionalArg("0") int i, @OptionalArg Player p) {
        CommandSender cs = (p == null) ? sender : p;
        switch (i) {
        case 1:
            messageSender.info(cs, str);
            break;
        case -1:
            messageSender.error(cs, str);
            break;
        default:
            messageSender.send(cs, str);  
        }
       
        return CommandResult.success();
    }
   
    @Command(struct = "demo player-only", aliases = "po")
    public CommandResult playerOnly(Player sender) {
        return CommandResult.success("This is a player only command.");
    }
   
}
```