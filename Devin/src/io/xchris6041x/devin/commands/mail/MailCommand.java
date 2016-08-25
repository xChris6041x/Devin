package io.xchris6041x.devin.commands.mail;

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
import io.xchris6041x.devin.mail.Mail;

/**
 * Shows a list of mail in your mailbox.
 * @author Christopher Bishop
 */
@CommandOptions(onlyPlayers = true, parameters = "[page]")
public class MailCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		MessageSender msgSender = Devin.getMessageSender();
		
		int page = 0;
		if(args.length > 0 && Validator.isInteger(args[0], Devin.getMessageSender())) {
			page = Integer.parseInt(args[0]) - 1;
		}
		
		List<String> mailList = new ArrayList<String>();
		Mail[] allMail = Devin.getMailService().getAllMail(p);
		
		for(int i = 0; i < allMail.length; i++) {
			mailList.add(i + "|" + allMail[i].getSubject() + " - " + allMail[i].getSender().getName());
		}
		
		
		msgSender.info(p, "Mail", "--------------------");
		msgSender.info(p, CommandUtils.pagination(mailList, 9, page));
		return true;
	}
	
}
