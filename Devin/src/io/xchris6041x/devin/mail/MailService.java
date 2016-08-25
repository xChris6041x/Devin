package io.xchris6041x.devin.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class MailService implements ConfigurationSerializable {

	private List<Mailbox> mailboxes;
	
	public MailService() {
		mailboxes = new ArrayList<Mailbox>();
	}
	@SuppressWarnings("unchecked")
	public MailService(Map<String, Object> map) {
		this();
		for(Entry<String, Object> entry : map.entrySet()) {
			mailboxes.add(new Mailbox(UUID.fromString(entry.getKey()), (List<Mail>) entry.getValue()));
		}
	}

	
	private Mailbox findMailbox(UUID owner) {
		for(Mailbox mailbox : mailboxes) {
			if(mailbox.getOwnerId().equals(owner)) return mailbox;
		}
		
		Mailbox mailbox = new Mailbox(owner);
		mailboxes.add(mailbox);
		
		return mailbox;
	}
	
	/**
	 * Send a message to a player by mail.
	 * @param sender
	 * @param receiver
	 * @param subject
	 * @param message
	 */
	public void sendMessage(Player sender, OfflinePlayer receiver, String subject, String message) {
		Mailbox mailbox = findMailbox(receiver.getUniqueId());
		
		Mail mail = new Mail(sender, receiver, subject, message);
		mailbox.getMail().add(mail); 
	}
	
	/**
	 * Send a message to a player by mail with an attachment.
	 * @param sender
	 * @param receiver
	 * @param subject
	 * @param message
	 * @param attachment
	 */
	public void sendMessage(Player sender, OfflinePlayer receiver, String subject, String message, IMailAttachment attachment) {
		Mailbox mailbox = findMailbox(receiver.getUniqueId());
		
		Mail mail = new AttachableMail(sender, receiver, subject, message, attachment);
		mailbox.getMail().add(mail); 
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		for(Mailbox mailbox : mailboxes) {
			map.put(mailbox.getOwnerId().toString(), mailbox.getMail());
		}
		
		return map;
	}
	
}
