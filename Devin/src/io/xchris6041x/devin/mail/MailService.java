package io.xchris6041x.devin.mail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Set;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class MailService {

	private File file;
	private List<Mailbox> mailboxes;
	
	public MailService(File file) {
		this.file = file;
		mailboxes = new ArrayList<Mailbox>();
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
		
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Open mail.
	 * @param player - The owner of the mail.
	 * @param index - The index of the mail in the mailbox.
	 * @return - The mail that was opened.
	 */
	public Mail openMail(Player player, int index) {
		Mail mail = getAllMail(player)[index];
		mail.setRead(true);
		
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mail;
	}
	
	/**
	 * Remove mail at a specific index from a players mailbox.
	 * @param player
	 * @param index
	 */
	public void removeMail(Player player, int index) {
		findMailbox(player.getUniqueId()).getMail().remove(index);
		
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param player
	 * @return all the mail in a player's mailbox.
	 */
	public Mail[] getAllMail(OfflinePlayer player) {
		Mailbox mailbox = findMailbox(player.getUniqueId());
		return mailbox.getMail().toArray(new Mail[0]);
	}
	
	/**
	 * Saves the MailService to the file.
	 * @throws IOException
	 */
	public void save() throws IOException {
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		for(Mailbox mailbox : mailboxes) {
			config.set(mailbox.getOwnerId().toString(), mailbox.getMail());
		}
		
		config.save(file);
	}
	
	/**
	 * Load the MailService from a file.
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static MailService load(File file) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		MailService service = new MailService(file);
		
		Set<String> uuids = config.getKeys(false);
		for(String uuid : uuids) {
			service.mailboxes.add(new Mailbox(UUID.fromString(uuid), (List<Mail>) config.get(uuid)));
		}
		
		return service;
	}
	
}
