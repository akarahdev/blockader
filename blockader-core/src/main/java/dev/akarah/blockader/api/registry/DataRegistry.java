package dev.akarah.blockader.api.registry;

import com.google.common.collect.Maps;
import net.kyori.adventure.key.Key;
import org.bukkit.Keyed;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DataRegistry<T extends Keyed> {
    Map<Key, T> map = Maps.newHashMap();

    public void insert(T value) {
        this.map.put(value.key(), value);
    }

    public Optional<T> get(Key key) {
        return Optional.ofNullable(this.map.getOrDefault(key, null));
    }

    public Set<Key> keySet() {
        return this.map.keySet();
    }
}
