package io.xchris6041x.devin.data;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Stores a UUID (the owner), Key, and Value to attach data to objects
 * which the equality of that object is defined by the UUID.
 *
 * @author Christopher Bishop
 */
@SerializableAs("UUIDProperty")
public class UUIDProperty implements ConfigurationSerializable {

    private UUID owner;
    private String key;
    private Object value;

    public UUIDProperty(UUID owner, String key, Object value) {
        this.owner = owner;
        this.key = key;
        this.value = value;
    }

    public UUIDProperty(Map<String, Object> map) {
        owner = UUID.fromString((String) map.get("owner"));
        key = (String) map.get("key");
        value = map.get("value");
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
     *
     * @param value
     */
    public void setValue(Object value) {
        this.value = value;
    }


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("owner", owner.toString());
        map.put("key", key);
        map.put("value", value);

        return map;
    }

}
