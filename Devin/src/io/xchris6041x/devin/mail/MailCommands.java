package io.xchris6041x.devin.mail;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import io.xchris6041x.devin.Devin;
import io.xchris6041x.devin.MessageSender;
import io.xchris6041x.devin.commands.ArgumentStream;
import io.xchris6041x.devin.commands.Command;
import io.xchris6041x.devin.commands.CommandResult;
import io.xchris6041x.devin.commands.CommandUtils;
import io.xchris6041x.devin.commands.Commandable;
import io.xchris6041x.devin.commands.DevinInject;
import io.xchris6041x.devin.commands.OptionalArg;
import net.md_5.bungee.api.ChatColor;

public class MailCommands implements Commandable {
	
	@DevinInject
	public MessageSender msgSender;
	
	@Command(struct = "mail", params = "page")
	public CommandResult listMail(Player p, @OptionalArg("1") int pageNumber) {
		Mail[] mailbox = Devin.getMailService().getAllMail(p);
		
		List<String> mailboxString = new ArrayList<String>();
		int unread = 0;
		
		for(int i = 0; i < mailbox.length; i++) {
			mailboxString.add((mailbox[i].wasRead() ? ChatColor.WHITE : ChatColor.GOLD) + "" + i + " | " + mailbox[i].toString());
			if(!mailbox[i].wasRead()) unread++;
		}
		
		
		msgSender.info(p, p.getName() + "'s Mailbox (" + unread + " unread)", "--------------------------------");
		msgSender.send(p, CommandUtils.pagination(mailboxString, 5, pageNumber - 1));
		
		return CommandResult.success();
	}
	
	@Command(struct = "mail open", params = "index")
	public CommandResult openMail(Player p, int index) {
		try {
			Mail mail = Devin.getMailService().openMail(p, index);
			msgSender.send(p, ChatColor.YELLOW + mail.toString(), "-----------------------------", mail.getMessage());
			
			return CommandResult.success();
		}
		catch(IndexOutOfBoundsException e) {
			return CommandResult.failed("Invalid index.");
		}
	}
	
	@Command(struct = "mail send", params = { "receiver", "subject", "message" })
	public CommandResult sendMail(Player p, Player receiver, String subject, ArgumentStream args) {
		Devin.getMailService().sendMessage(p, receiver, subject, args.implode());
		
		msgSender.info(receiver, "You received mail from " + p.getName());
		return CommandResult.success("Successfully sent mail");
	}
	
}
