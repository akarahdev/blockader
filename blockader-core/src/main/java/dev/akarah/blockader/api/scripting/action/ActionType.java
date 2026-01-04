package dev.akarah.blockader.api.scripting.action;

import org.bukkit.Keyed;

public interface ActionType extends Keyed {
    Icon icon();
    String signId();
}
