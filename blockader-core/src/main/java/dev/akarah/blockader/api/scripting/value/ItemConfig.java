package dev.akarah.blockader.api.scripting.value;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemType;

import java.util.List;

public interface ItemConfig<C> {
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
