package io.xchris6041x.devin.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.xchris6041x.devin.MessageSender;

class CommandHandler extends CommandHandlerContainer implements CommandExecutor {
	
	private CommandMethod method;
	private MessageSender msgSender;
	
	public CommandHandler(MessageSender msgSender){
		this.msgSender = msgSender;
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
				String[] newArgs = new String[args.length - 1];
				for(int i = 1; i < args.length; i++) {
					newArgs[i - 1] = args[i];
				}
				
				handler.onCommand(sender, cmd, label + " " + sub, newArgs);
			}
		}
		
		// Execute method.
		if(method.size() >= args.length) {
			Object[] methodArgs = new Object[method.size()];
			for(int i = 0; i < args.length && i < methodArgs.length; i++) {
				methodArgs[i] = args[i];
			}
			
			return true;
		}
		return false;
	}
	
}
