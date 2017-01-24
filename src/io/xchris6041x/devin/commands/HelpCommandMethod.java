package io.xchris6041x.devin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;
import io.xchris6041x.devin.Validator;

class HelpCommandMethod implements CommandMethod {

	private String usage;
	
	public HelpCommandMethod(CommandHandler root, String name) {
		this.usage = root.getName() + " " + name + " [page]";
	}
	
	@Override
	public String getUsage() {
		return usage;
	}

	
	@Override
	public void invoke(CommandHandler handler, CommandSender sender, String[] rawArgs, MessageSender msgSender) throws DevinException {
		CommandHandlerContainer root = handler.getParent();
		List<String> helpMessages = new ArrayList<String>();
		buildHelpMessages(helpMessages, root);
		
		int page = 0;
		if(rawArgs.length > 0 && Validator.isInteger(rawArgs[0], sender, msgSender)) {
			page = Integer.parseInt(rawArgs[0]) - 1;
		}
		
		msgSender.send(sender, CommandUtils.pagination(helpMessages, 7, page));
	}
	
	
	private void buildHelpMessages(List<String> helpMessages, CommandHandlerContainer container) {
		if(container instanceof CommandHandler) {
			CommandHandler handler = (CommandHandler) container;
			helpMessages.add(handler.getMethod().getUsage() + " - " + handler.getDescription());
		}
		
		// Build messages for children.
		for(CommandHandlerContainer chc : container.getChildren()) {
			buildHelpMessages(helpMessages, chc);
		}
	}
	
}
