package dev.akarah.blockader.api.scripting;

import dev.akarah.blockader.api.registry.DataRegistries;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Optional;

public interface ValueType<P, C> extends Keyed {
    PersistentDataType<P, C> persistentDataType();
    C defaultValue();

    Optional<ItemConfig<C>> item();

    interface ItemConfig<C> {
        default ItemType itemType(C value) {
            return ItemType.SHULKER_SHELL;
        }

        Component itemName(C value);

        default List<Component> itemLoreFromValue(C value) {
            return List.of();
        }

        default C editRightClick(Player player, C value) {
            throw new UnsupportedOperationException();
        }

        default C editChat(Player player, C value, String message) {
            throw new UnsupportedOperationException();
        }
    }

    default ItemStack defaultItem() {
        return createItemFromValue(defaultValue());
    }

    default ItemStack createItemFromValue(C value) {
        if(this.item().isEmpty()) {
            return ItemStack.empty();
        }
        var is = this.item().orElseThrow().itemType(value).createItemStack();
        is.editPersistentDataContainer(pdc -> {
            pdc.set(
                    new NamespacedKey("blockcoder", "value/data"),
                    this.persistentDataType(),
                    value
            );
            pdc.set(
                    new NamespacedKey("blockcoder", "value/id"),
                    PersistentDataType.STRING,
                    this.key().toString()
            );
        });
        is.editMeta(meta -> {
            meta.itemName(this.item().orElseThrow().itemName(value));
            meta.lore(this.item().orElseThrow().itemLoreFromValue(value));
        });
        return is;
    }

    default C createDataFromItemStack(ItemStack is) {
        return is.getPersistentDataContainer()
                .get(
                        new NamespacedKey("blockcoder", "value/data"),
                        this.persistentDataType()
                );
    }

    static Optional<ValueType<?, ?>> valueTypeOfItem(ItemStack is) {
        return Optional.ofNullable(
                is.getPersistentDataContainer()
                        .get(
                                new NamespacedKey("blockcoder", "value/id"),
                                PersistentDataType.STRING
                        )
        )
                .map(Key::key)
                .flatMap(key -> DataRegistries.VALUE_TYPES.get(key));
    }
}
