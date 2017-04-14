package io.xchris6041x.devin.data;

import java.util.UUID;

@SuppressWarnings("ALL")
public abstract class GenericUUIDPropertyCollection<T> extends UUIDPropertyCollection {

    protected abstract UUID getUUIDFrom(T obj);

    public <E> E get(T owner, String key, E def) {
        return get(getUUIDFrom(owner), key, def);
    }

    public <E> E get(T owner, String key) {
        return get(owner, key, null);
    }

    public boolean exists(T owner, String key) {
        return exists(getUUIDFrom(owner), key);
    }

    public <E> void set(T owner, String key, E value) {
        set(getUUIDFrom(owner), key, value);
    }

}
