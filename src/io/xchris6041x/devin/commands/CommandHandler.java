package io.xchris6041x.devin.commands;

import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

class CommandHandler extends CommandHandlerContainer implements CommandExecutor {

    private CommandMethod method;

    public CommandHandler(String name, MessageSender msgSender) {
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
        if (args.length > 0) {
            String sub = args[0];

            CommandHandler handler = getHandler(sub);
            if (handler != null) {
                // Remove first arg.
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, args.length - 1);

                return handler.onCommand(sender, cmd, label + " " + sub, newArgs);
            }
        }

        // Execute command.
        if (method == null) {
            getMessageSender().error("Invalid command " + label);
        } else {
            try {
                method.invoke(this, sender, args, getMessageSender());
            } catch (DevinException e) {
                if (e.getCause() != null) {
                    e.printStackTrace();
                    getMessageSender().error(sender, "Internal server error.");
                } else {
                    getMessageSender().error(sender, e.getMessage());
                }
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        //
        // This method tab completes for arguments such as players and enums
        //
        List<String> completions = super.onTabComplete(sender, cmd, label, args);
        if (method == null) return completions;

        Class<?>[] types = method.getArgumentTypes();
        if (types.length >= args.length) {
            Class<?> type = types[args.length - 1];
            String arg = args[args.length - 1];

            if (type == Player.class || type == OfflinePlayer.class) {
                // Tab complete players.
                List<String> players = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    players.add(p.getName());
                }
                StringUtil.copyPartialMatches(arg, players, completions);
            } else if (type.isEnum()) {
                // Tab complete enums.
                List<String> values = new ArrayList<>();
                for (Object value : type.getEnumConstants()) {
                    values.add(value.toString());
                }
                StringUtil.copyPartialMatches(arg, values, completions);
            }
        }
        return completions;
    }
}
