package dev.akarah.blockader.api.scripting.action;

import dev.akarah.blockader.api.registry.DataRegistry;
import org.bukkit.Keyed;
import org.bukkit.block.BlockType;

public interface CodeBlockType extends Keyed {
    BlockType blockType();
    String name();
    DataRegistry<ActionType> actionRegistry();

    boolean hasParameters();
    boolean hasBrackets();
    boolean isLineStarter();
}
