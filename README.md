![DEVIN](http://gdurl.com/yp4P)

DEVIN is a framework for Spigot 1.10+ that gives developers the tools to build better and bigger plugins more efficiently. DEVIN takes care of a lot of the cumbersome code that developers would have to traditionally write so they can spend more time developing a better plugin.

| Features | Progress |
| --- | :-: |
| `MessageSender` Simplifies sending messages with multi-messages in one line and prefixes. | 100% |
| A powerful annotated command framework that can automatically register commands and permissions without `plugin.yml`. | 90% |
| `CommandUtils` is a utility class that has common yet complicated tasks such as pagination | 100% |
| Data API for temporary and persistent data that can have owners such as players and worlds. _This is getting an overhaul from the PlayerData API._ | 20% |
| Mailing API (which can be turned off) for sending mail with a powerful attachment API. | 90% |

## Requirements
- Server is running Java 8.
- Server is running SpigotMC 1.10+

## Start Developing
If you want to start developing today, before I release the DEVIN.jar, you can download the source code and compile it.
_WARNING: Things may be removed or renamed without deprecation. This will change once I upload the first jar file._

If you are just learning DEVIN for the first time, read through the README and then head over to the [wiki](https://github.com/xChris6041x/Devin/wiki). The wiki will give you much more detailed information.

### Setting Up
1. Reference the JAR file in your project.
2. You're all set!

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