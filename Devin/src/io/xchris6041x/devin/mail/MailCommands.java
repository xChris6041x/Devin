package io.xchris6041x.devin.mail;

import org.bukkit.entity.Player;

import io.xchris6041x.devin.MessageSender;
import io.xchris6041x.devin.commands.Command;
import io.xchris6041x.devin.commands.Commandable;
import io.xchris6041x.devin.commands.Inject;

public class MailCommands implements Commandable {

	@Inject
	public MessageSender msgSender;
	
	@Command(struct = "mail")
	public boolean listMail(Player p) {
		
		return true;
	}
	
}
