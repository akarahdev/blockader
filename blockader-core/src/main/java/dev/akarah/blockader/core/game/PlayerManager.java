package dev.akarah.blockader.core.game;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    Map<UUID, Mode> playerModes = Maps.newHashMap();

    public enum Mode {
        PLAY,
        EDIT
    }

    public void setMode(Player player, Mode mode) {
        playerModes.put(player.getUniqueId(), mode);
    }

    public Mode mode(Player player) {
        return playerModes.getOrDefault(player.getUniqueId(), Mode.PLAY);
    }
}
