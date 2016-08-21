package io.xchris6041x.devin.playerdata;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class PlayerData implements ConfigurationSerializable {

	private UUID owner;
	
	private Map<String, Object> temperary = new HashMap<String, Object>();
	private Map<String, Object> persistant;
	
	protected PlayerData(UUID owner) {
		this.owner = owner;
	}
	@SuppressWarnings("unchecked")
	public PlayerData(Map<String, Object> map) {
		owner = UUID.fromString((String) map.get("owner"));
		persistant = (Map<String, Object>) map.get("persist");
	}
	
	/**
	 * @return the player's UUID that this PlayerData belongs to.
	 */
	public UUID getOwnerId() {
		return owner;
	}
	
	/**
	 * @param key
	 * @return the element with the same type as T and the same key.
	 */
	public <T> T get(String key) {
		try {
			T value = getFromMap(key, temperary);
			if(value != null) return value;
			
			value = getFromMap(key, persistant);
			return value;
		}
		catch(ClassCastException e) {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	private <T> T getFromMap(String key, Map<String, Object> map) throws ClassCastException {
		for(Entry<String, Object> entry : map.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(key) && entry.getValue() != null) {
				return (T) entry.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * Set a temperary value inside the PlayerData. This removes any persistant value with the same key.
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		removeFromMap(key, persistant);
		for(Entry<String, Object> entry : temperary.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(key)) {
				temperary.put(entry.getKey(), value);
				return;
			}
		}
		
		temperary.put(key, value);
	}
	
	/**
	 * Set a persistant value inside the PlayerData. This removes any temperary value with the same key.
	 * @param key
	 * @param value
	 */
	public void persist(String key, Object value) {
		removeFromMap(key, temperary);
		for(Entry<String, Object> entry : temperary.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(key)) {
				persistant.put(entry.getKey(), value);
				return;
			}
		}
		
		persistant.put(key, value);
	}
	
	/**
	 * Remove an element with the same key from either temperary or persistant.
	 * @param key
	 */
	public void remove(String key) {
		removeFromMap(key, temperary);
		removeFromMap(key, persistant);
	}
	private void removeFromMap(String key, Map<String, Object> map) {
		for(Entry<String, Object> entry : map.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(key)) {
				map.remove(entry.getKey());
				break;
			}
		}
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("owner", owner.toString());
		map.put("persist", persistant);
		
		return map;
	}
	
}
