package dev.akarah.blockader.game;

import org.bukkit.World;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record LoadedGame(
        UUID uuid,
        World gameWorld,
        World codeWorld
) implements PersistentDataHolder {
    @Override
    public @NotNull PersistentDataContainer getPersistentDataContainer() {
        return gameWorld.getPersistentDataContainer();
    }
}
