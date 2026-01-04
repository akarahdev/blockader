package dev.akarah.blockader.stdlib.codeblocks;

import dev.akarah.blockader.api.registry.DataRegistry;
import dev.akarah.blockader.api.scripting.action.ActionType;
import dev.akarah.blockader.api.scripting.action.CodeBlockType;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.jetbrains.annotations.NotNull;

public class PlayerEvent implements CodeBlockType {
    DataRegistry<ActionType> actionTypeDataRegistry = new DataRegistry<>();

    @Override
    public BlockType blockType() {
        return BlockType.DIAMOND_BLOCK;
    }

    @Override
    public String name() {
        return "Player Event";
    }

    @Override
    public DataRegistry<ActionType> actionRegistry() {
        return this.actionTypeDataRegistry;
    }

    @Override
    public boolean hasParameters() {
        return false;
    }

    @Override
    public boolean hasBrackets() {
        return false;
    }

    @Override
    public boolean isLineStarter() {
        return true;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey("std", "player/event");
    }
}
