package io.xchris6041x.devin.mail;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface IMailAttachment extends ConfigurationSerializable {
	
	public String getUsage();
	public boolean use(AttachableMail mail, String args[]);
	
}
