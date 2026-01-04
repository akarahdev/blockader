package dev.akarah.blockader.api.registry;

import com.google.common.collect.Maps;
import net.kyori.adventure.key.Key;
import org.bukkit.Keyed;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DataRegistry<T extends Keyed> {
    Map<Key, T> map = Maps.newHashMap();

    public void insert(T value) {
        this.map.put(value.key(), value);
    }

    public Optional<T> get(Key key) {
        return Optional.ofNullable(this.map.getOrDefault(key, null));
    }

    public Set<T> valueSet() {
        return new HashSet<>(this.map.values());
    }

    public Set<Key> keySet() {
        return this.map.keySet();
    }
}
