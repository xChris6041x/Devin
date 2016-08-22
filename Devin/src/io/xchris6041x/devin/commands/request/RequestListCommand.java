package io.xchris6041x.devin.commands.request;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.xchris6041x.devin.Devin;
import io.xchris6041x.devin.MessageSender;
import io.xchris6041x.devin.Validator;
import io.xchris6041x.devin.commands.CommandOptions;
import io.xchris6041x.devin.commands.CommandUtils;
import io.xchris6041x.devin.playerdata.PlayerData;
import io.xchris6041x.devin.playerdata.request.PlayerRequest;
import io.xchris6041x.devin.playerdata.request.RequestOptions;

@CommandOptions(onlyPlayers = true, parameters = "[page]")
public class RequestListCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		PlayerData data = Devin.getPlayerData(p);		
		MessageSender msgSender = Devin.getMessageSender();
		
		List<String> book = new ArrayList<String>();
		PlayerRequest[] requests = data.getRequests();
		
		for(int i = 0; i < requests.length; i++) {
			RequestOptions ro = requests[i].getClass().getAnnotation(RequestOptions.class);
			String message = i + "|";
			
			if(ro == null) {
				message += "Request: " + requests[i].getDescription() + " (Accept/Decline)";
			}
			else{
				message += ro.name() + ": " + requests[i].getDescription() + " (" + ro.responses()[0];
				for(int j = 1; j < ro.responses().length; j++) {
					message += "/" + ro.responses()[j];
				}
				
				message += ")";
			}
			
			book.add(message);
		}
		
		int pageNumber = 0;
		if(args.length > 1 && Validator.isInteger(args[0])) {
			pageNumber = Integer.parseInt(args[0]) - 1;
		}
		msgSender.info(sender, CommandUtils.pagination(book, 7, pageNumber));
		
		return true;
	}
	
}
