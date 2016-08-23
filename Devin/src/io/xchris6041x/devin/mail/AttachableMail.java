package io.xchris6041x.devin.mail;

import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class AttachableMail extends Mail {

	private IMailAttachment attachment;
	
	public AttachableMail(Player sender, OfflinePlayer receiver, String subject, String message, IMailAttachment attachment) {
		super(sender, receiver, subject, message);
		this.attachment = attachment;
	}
	public AttachableMail(Map<String, Object> map) {
		super(map);
		attachment = (IMailAttachment) map.get("attachment");
	}
	
	/**
	 * @return the attachment attched to this mail.
	 */
	public IMailAttachment getAttachment() {
		return attachment;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = super.serialize();
		map.put("attachment", attachment);
		
		return map;
	}

}
