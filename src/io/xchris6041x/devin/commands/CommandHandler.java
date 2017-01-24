package io.xchris6041x.devin.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;

class CommandHandler extends CommandHandlerContainer implements CommandExecutor {
	
	private ICommandMethod method;
	
	public CommandHandler(String name, MessageSender msgSender){
		super(name, msgSender);
	}
	
	public ICommandMethod getMethod() {
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
				for(int i = 1; i < args.length; i++) {
					newArgs[i - 1] = args[i];
				}
				
				return handler.onCommand(sender, cmd, label + " " + sub, newArgs);
			}
		}
		
		// Execute command.
		if(method == null) {
			getMessageSender().error("Invalid command " + label);
			return true;
		}
		else {
			try {
				method.invoke(sender, args, getMessageSender());
			} catch (DevinException e) {
				if(e.getCause() != null) {
					e.printStackTrace();
					getMessageSender().error(sender, "Internal server error.");
				}
				else {
					getMessageSender().error(sender, e.getMessage());
				}
			}
			
			return true;
		}
	}
	
}
