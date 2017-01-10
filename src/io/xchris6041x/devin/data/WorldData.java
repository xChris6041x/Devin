package io.xchris6041x.devin.data;

import java.util.UUID;

import org.bukkit.World;

public class WorldData extends UUIDPropertyData<World> {

	@Override
	protected UUID getUUIDFrom(World obj) {
		return obj.getUID();
	}
	
	
}
