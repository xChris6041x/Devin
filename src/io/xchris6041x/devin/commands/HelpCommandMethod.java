package io.xchris6041x.devin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;
import io.xchris6041x.devin.Validator;
import io.xchris6041x.devin.utils.CommandUtil;

class HelpCommandMethod implements CommandMethod {

	private String usage;
	
	public HelpCommandMethod(CommandHandler root, String name) {
		this.usage = "/" + root.getName() + " " + name + " [page]";
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
		
		int pageLength = 7;
		int maxPages = (int) Math.ceil(helpMessages.size() / (double) pageLength);
		int page = 0;
		
		if(rawArgs.length > 0) {
			if(!Validator.isInteger(rawArgs[0], sender, msgSender)) return;
			page = Integer.parseInt(rawArgs[0]) - 1;
		}
		
		if(page > maxPages - 1) {
			page = maxPages - 1;
		}
		else if(page < 0) {
			page = 0;
		}
		
		msgSender.info(sender, root.getName().toUpperCase() + " Commands | Page " + (page + 1) + "/" + maxPages);
		msgSender.send(sender, "------------------------------------------------");
		msgSender.send(sender, CommandUtil.pagination(helpMessages, pageLength, page));
	}
	
	
	private void buildHelpMessages(List<String> helpMessages, CommandHandlerContainer container) {
		if(container instanceof CommandHandler && ((CommandHandler)container).getMethod() != null) {
			CommandHandler handler = (CommandHandler) container;
			helpMessages.add(handler.getMethod().getUsage() + (handler.getDescription().length() > 0 ? " - " + handler.getDescription() : ""));
		}
		
		// Build messages for children.
		for(CommandHandlerContainer chc : container.getChildren()) {
			buildHelpMessages(helpMessages, chc);
		}
	}
	
}
