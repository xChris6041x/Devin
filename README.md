![DEVIN](http://gdurl.com/yp4P)

DEVIN is a framework for Spigot 1.10+ that gives developers the tools to build better and bigger plugins more efficiently. DEVIN takes care of a lot of the cumbersome code that developers would have to traditionally write so they can spend more time developing a better plugin.

| Features | Progress |
| --- | :-: |
| `MessageSender` Simplifies sending messages with multi-messages in one line and prefixes. | 100% |
| A powerful annotated command framework that can automatically register commands and permissions without `plugin.yml`. | 90% |
| `CommandUtils` is a utility class that has common yet complicated tasks such as pagination | 100% |
| Data API for temporary and persistent data that can have owners such as players and worlds. _This is getting an overhaul from the PlayerData API._ | 20% |
| Mailing API (which can be turned off) for sending mail with a powerful attachment API. | 90% |

## Command Framework
Demo of what the command-framework can do.
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