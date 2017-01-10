package io.xchris6041x.devin.data;

import java.util.UUID;

/**
 * Stores a UUID (the owner), Key, and Value to attach data to objects
 * which the equality of that object is defined by the UUID.
 * @author Christopher Bishop
 */
public class UUIDProperty {

	private UUID owner;
	private String key;
	private Object value;
	
	public UUIDProperty(UUID owner, String key, Object value) {
		this.owner = owner;
		this.key = key;
		this.value = value;
	}
	
	
	/**
	 * @return the UUID of the owner.
	 */
	public UUID getUUID() { 
		return owner;
	}
	
	/**
	 * @return the key of the property.
	 */
	public String getKey() { 
		return key; 
	}
	
	/**
	 * @return the value of the property.
	 */
	public Object getValue() { 
		return value; 
	}
	
	/**
	 * Set the value of the property.
	 * @param value
	 */
	public void setValue(Object value) { 
		this.value = value; 
	}
	
}
