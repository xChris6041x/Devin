package io.xchris6041x.devin.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

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

	
	
	
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		for(Mailbox mailbox : mailboxes) {
			map.put(mailbox.getOwnerId().toString(), mailbox.getMail());
		}
		
		return map;
	}
	
}
