package dev.akarah.blockader.stdlib.actions.pevent;

import dev.akarah.blockader.api.scripting.action.ActionType;
import dev.akarah.blockader.api.scripting.action.Icon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;

public class LeftClick implements ActionType {
    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey("std", "player/event/left_click");
    }

    @Override
    public String signId() {
        return "LeftClick";
    }

    @Override
    public Icon icon() {
        return Icon.of(ItemType.DIAMOND_SWORD)
                .named(Component.text("Player Left Click").color(TextColor.color(100, 100, 255)))
                .description(
                        Component.text("Executes when a player left clicks.")
                                .color(TextColor.color(150, 150, 150))
                );
    }
}
