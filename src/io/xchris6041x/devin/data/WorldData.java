package io.xchris6041x.devin.data;

import java.util.UUID;

import org.bukkit.World;

public class WorldData extends GenericUUIDPropertyCollection<World> {

	@Override
	protected UUID getUUIDFrom(World obj) {
		return obj.getUID();
	}
	
	
}
