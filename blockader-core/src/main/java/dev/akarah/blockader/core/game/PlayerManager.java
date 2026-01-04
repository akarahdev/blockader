package dev.akarah.blockader.core.game;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    Map<UUID, Mode> playerModes = Maps.newHashMap();

    public enum Mode {
        PLAY,
        CODE
    }
}
