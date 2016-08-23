package io.xchris6041x.devin.mail;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface MailAttachment extends ConfigurationSerializable {

	public boolean use(AttachableMail mail, String args[]);
	
}
