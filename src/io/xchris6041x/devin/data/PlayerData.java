package io.xchris6041x.devin.data;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class PlayerData extends GenericUUIDPropertyCollection<OfflinePlayer> {

    @Override
    protected UUID getUUIDFrom(OfflinePlayer obj) {
        return obj.getUniqueId();
    }

}
