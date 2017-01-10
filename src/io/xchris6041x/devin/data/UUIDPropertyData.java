package io.xchris6041x.devin.data;

import java.util.UUID;

public abstract class UUIDPropertyData<T> {
	
	private UUIDPropertyCollection tempProperties;
	
	public UUIDPropertyData() {
		tempProperties = new UUIDPropertyCollection();
	}
	
	public <E> E get(T owner, String key, E def) {
		return tempProperties.get(getUUIDFrom(owner), key, def);
	}
	public <E> E get(T owner, String key) {
		return get(owner, key, null);
	}
	
	public boolean exists(T owner, String key) {
		return tempProperties.exists(getUUIDFrom(owner), key);
	}
	
	public <E> void set(T owner, String key, E value) {
		tempProperties.set(getUUIDFrom(owner), key, value);
	}
	
	
	protected abstract UUID getUUIDFrom(T obj);
	
}
