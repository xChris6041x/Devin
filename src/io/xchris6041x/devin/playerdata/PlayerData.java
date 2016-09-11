package io.xchris6041x.devin.playerdata;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.plugin.Plugin;

/**
 * Custom data that is linked to a player.
 * @author Christopher Bishop
 */
public class PlayerData implements ConfigurationSerializable {

	private UUID owner;
	
	private Map<String, Object> temperary = new HashMap<String, Object>();
	private Map<String, Object> persistent;
	
	protected PlayerData(UUID owner) {
		this.owner = owner;
		persistent = new HashMap<String, Object>();
	}
	@SuppressWarnings("unchecked")
	public PlayerData(Map<String, Object> map) {
		owner = UUID.fromString((String) map.get("owner"));
		persistent = (Map<String, Object>) map.get("persist");
	}
	
	/**
	 * @return the player's UUID that this PlayerData belongs to.
	 */
	public UUID getOwnerId() {
		return owner;
	}
	
	/**
	 * @param key
	 * @param def
	 * @param persistent - Whether the data that is being retreived is persistent.
	 * @return the element with the same type as T and the same key.
	 */
	public <T> T get(Plugin plugin, String key, T def, boolean persistent) {
		try {
			String fullKey = toFullKey(plugin, key);
			return getFromMap(fullKey, (persistent) ? this.persistent : temperary, def);
		}
		catch(ClassCastException e) {
			return def;
		}
	}
	@SuppressWarnings("unchecked")
	private <T> T getFromMap(String key, Map<String, Object> map, T def) throws ClassCastException {
		for(Entry<String, Object> entry : map.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(key) && entry.getValue() != null) {
				return (T) entry.getValue();
			}
		}
		
		return def;
	}
	
	/**
	 * Set a value inside the PlayerData.
	 * @param key
	 * @param value
	 * @param persist - Whether the value should be saved to the playerdata.yml
	 */
	public <T> void set(Plugin plugin, String key, T value, boolean persist) {
		String fullKey = toFullKey(plugin, key);
		Map<String, Object> map = (persist) ? persistent : temperary;
		
		for(Entry<String, Object> entry : map.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(fullKey)) {
				map.put(entry.getKey(), value);
				return;
			}
		}
		
		map.put(fullKey, value);
	}
	
	/**
	 * Remove an element with the same key from either temperary or persistant.
	 * @param key
	 */
	public void remove(Plugin plugin, String key) {
		String fullKey = toFullKey(plugin, key);
		removeFromMap(fullKey, temperary);
		removeFromMap(fullKey, persistent);
	}
	private void removeFromMap(String key, Map<String, Object> map) {
		for(Entry<String, Object> entry : map.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(key)) {
				map.remove(entry.getKey());
				break;
			}
		}
	}
	
	private String toFullKey(Plugin plugin, String key) {
		return (plugin.getClass().getPackage().getName() + key).toLowerCase();
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("owner", owner.toString());
		map.put("persist", persistent);
		
		return map;
	}
	
}
