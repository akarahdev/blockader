package dev.akarah.blockader.stdlib.values;

import dev.akarah.blockader.api.scripting.value.ItemConfig;
import dev.akarah.blockader.api.scripting.value.ValueType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemType;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class NumberType implements ValueType<Double, Double> {
    @Override
    public PersistentDataType<Double, Double> persistentDataType() {
        return PersistentDataType.DOUBLE;
    }

    @Override
    public Optional<ItemConfig<Double>> item() {
        return Optional.of(new ItemConfig<>() {
            @Override
            public ItemType itemType(Double value) {
                return ItemType.SLIME_BALL;
            }

            @Override
            public Component itemName(Double value) {
                return Component.text(value.toString()).color(TextColor.color(255, 100, 100));
            }

            @Override
            public Double editChat(Player player, Double value, String message) {
                try {
                    return Double.parseDouble(message);
                } catch (Exception e) {
                    return 0.0;
                }
            }
        });
    }

    @Override
    public Double defaultValue() {
        return 0.0;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey("std", "number");
    }
}
