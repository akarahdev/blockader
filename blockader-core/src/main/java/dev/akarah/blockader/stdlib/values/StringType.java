package dev.akarah.blockader.stdlib.values;

import dev.akarah.blockader.api.scripting.value.ItemConfig;
import dev.akarah.blockader.api.scripting.value.ValueType;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemType;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class StringType implements ValueType<String, String> {
    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey("std", "string");
    }

    @Override
    public PersistentDataType<String, String> persistentDataType() {
        return PersistentDataType.STRING;
    }

    @Override
    public String defaultValue() {
        return "";
    }

    @Override
    public Optional<ItemConfig<String>> item() {
        return Optional.of(new ItemConfig<>() {
            @Override
            public ItemType itemType(String value) {
                return ItemType.STRING;
            }

            @Override
            public Component itemName(String value) {
                return Component.text(value);
            }

            @Override
            public String editChat(Player player, String value, String message) {
                return message;
            }
        });
    }
}
