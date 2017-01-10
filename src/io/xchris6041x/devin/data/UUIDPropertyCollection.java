package io.xchris6041x.devin.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDPropertyCollection {

	private List<UUIDProperty> properties;
	
	public UUIDPropertyCollection() {
		properties = new ArrayList<UUIDProperty>();
	}
	
	/**
	 * @param owner
	 * @param key
	 * @param def
	 * @return The value inside the property with the owner and key. If no property exists returns the default.
	 */
	public <T> T get(UUID owner, String key, T def) {
		for(UUIDProperty property : properties) {
			if(property.getUUID().equals(owner) && property.getKey().equalsIgnoreCase(key)) {
				
				@SuppressWarnings("unchecked")
				T value = (T) property.getValue();
				return value;
				
			}
		}
		return def;
	}
	/**
	 * 
	 * @param owner
	 * @param key
	 * @return The value inside the property with the owner and key. If no property exists returns null.
	 */
	public <T> T get(UUID owner, String key) {
		return get(owner, key, null);
	}
	
	/**
	 * @param owner
	 * @param key
	 * @return Whether a property with the owner and key exists.
	 */
	public boolean exists(UUID owner, String key) {
		for(UUIDProperty property : properties) {
			if(property.getUUID().equals(owner) && property.getKey().equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Set a property's value with the owner and key to the value. If none exists it is created.
	 * @param owner
	 * @param key
	 * @param value
	 */
	public <T> void set(UUID owner, String key, T value) {
		boolean foundMatch = false;
		for(UUIDProperty property : properties) {
			if(property.getUUID().equals(owner) && property.getKey().equalsIgnoreCase(key)) {
				
				property.setValue(value);
				
				foundMatch = true;
				break;
			}
		}
		
		if(!foundMatch) properties.add(new UUIDProperty(owner, key, value));
	}
	
}
