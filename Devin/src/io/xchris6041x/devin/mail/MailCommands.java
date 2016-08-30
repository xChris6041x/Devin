package io.xchris6041x.devin.mail;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import io.xchris6041x.devin.Devin;
import io.xchris6041x.devin.MessageSender;
import io.xchris6041x.devin.commands.ArgumentStream;
import io.xchris6041x.devin.commands.Command;
import io.xchris6041x.devin.commands.CommandUtils;
import io.xchris6041x.devin.commands.Commandable;
import io.xchris6041x.devin.commands.DevinInject;

public class MailCommands implements Commandable {
	
	@DevinInject
	public MessageSender msgSender;
	
	@Command(struct = "mail", params = { "page" })
	public boolean listMail(Player p, int pageNumber) {
		Mail[] mailbox = Devin.getMailService().getAllMail(p);
		
		msgSender.info(p, p.getName() + "'s Mailbox (" + mailbox.length + ")");
		msgSender.info(p, "-----------------------------");
		
		List<String> mailboxString = new ArrayList<String>();
		for(int i = 0; i < mailbox.length; i++) {
			mailboxString.add(i + " | " + mailbox[i].toString());
		}
		
		String[] page = CommandUtils.pagination(mailboxString, 5, pageNumber - 1);
		msgSender.info(p, page);
		
		return true;
	}
	
	@Command(struct = "mail send", params = { "receiver", "subject", "message" })
	public boolean sendMail(Player p, Player receiver, String subject, ArgumentStream args) {
		Devin.getMailService().sendMessage(p, receiver, subject, args.implode());
		
		msgSender.info(p, "Successfully sent mail.");
		msgSender.info(receiver, "You received mail from " + p.getName());
		
		return true;
	}
	
}
