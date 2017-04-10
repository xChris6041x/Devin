package io.xchris6041x.devin.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;

import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

class CommandHandler extends CommandHandlerContainer implements CommandExecutor {
	
	private CommandMethod method;
	
	public CommandHandler(String name, MessageSender msgSender){
        super(name, msgSender);
    }
	
	public CommandMethod getMethod() {
		return method;
	}
	public void setMethod(CommandMethod method) {
		this.method = method;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		// Check if it belongs to sub-command.
		if(args.length > 0) {
			String sub = args[0];
			
			CommandHandler handler = getHandler(sub);
			if(handler != null) {
				// Remove first arg.
				String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, args.length - 1);
				
				return handler.onCommand(sender, cmd, label + " " + sub, newArgs);
			}
		}
		
		// Execute command.
		if(method == null) {
			getMessageSender().error("Invalid command " + label);
		}
		else {
			try {
				method.invoke(this, sender, args, getMessageSender());
			} catch (DevinException e) {
				if(e.getCause() != null) {
					e.printStackTrace();
					getMessageSender().error(sender, "Internal server error.");
				}
				else {
					getMessageSender().error(sender, e.getMessage());
				}
			}
		}
		
		return true;
	}

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions =  super.onTabComplete(sender, cmd, label, args);
        if(method != null) {
            Class<?>[] types = method.getArgumentTypes();
            if (types.length >= args.length) {
                Class<?> type = method.getArgumentTypes()[args.length - 1];
                if (type == Player.class || type == OfflinePlayer.class) {
                    Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                    for (Player p : players) {
                        String name = p.getName();
                        if (name.toLowerCase().contains(args[args.length - 1].toLowerCase())) {
                            completions.add(name);
                        }
                    }
                }
            }
        }

        return completions;
    }
}
