package io.xchris6041x.devin.data;

import org.bukkit.World;

import java.util.UUID;

public class WorldData extends GenericUUIDPropertyCollection<World> {

    @Override
    protected UUID getUUIDFrom(World obj) {
        return obj.getUID();
    }


}
