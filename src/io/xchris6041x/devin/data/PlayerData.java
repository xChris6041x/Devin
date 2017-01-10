package io.xchris6041x.devin.data;

import java.util.UUID;

import org.bukkit.OfflinePlayer;

public class PlayerData extends UUIDPropertyData<OfflinePlayer> {

	@Override
	protected UUID getUUIDFrom(OfflinePlayer obj) {
		return obj.getUniqueId();
	}
	
}
