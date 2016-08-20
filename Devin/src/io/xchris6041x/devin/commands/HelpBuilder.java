package io.xchris6041x.devin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class HelpBuilder {

	public static CommandExecutor build(final String[] help) {
		return new CommandExecutor() {
			
			@Override
			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				int page = 0;
				if(args.length > 0) {
					if(Validator.isInteger(args[0])) {
						page = Integer.parseInt(args[0]) - 1;
						if(page < 0) {
							page = 0;
						}
						else if(page > Math.ceil(args.length / 5.0)) {
							page = (int) Math.ceil(args.length / 5.0);
						}
					}
					else{
						return true;
					}
				}
				
				for(int i = 0; i < help.length; i++) {
					
				}
				return true;
			}
			
		};
	}
	
}
