package io.xchris6041x.devin.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Mailbox {

	private UUID owner;
	private List<Mail> mail;
	
	public Mailbox(UUID owner, List<Mail> mail) {
		this.owner = owner;
		this.mail = mail;
	}
	public Mailbox(UUID owner) {
		this(owner, new ArrayList<Mail>());
	}
	
	public UUID getOwnerId() {
		return owner;
	}
	public List<Mail> getMail() {
		return mail;
	}
	
}
