package dev.akarah.blockader;

import dev.akarah.blockader.core.game.GameManager;
import dev.akarah.blockader.core.game.PlayerManager;
import dev.akarah.blockader.core.game.dev.DevEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class Blockader extends JavaPlugin {
    GameManager gameManager = new GameManager();
    PlayerManager playerManager = new PlayerManager();
    static Blockader INSTANCE;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Blockader.INSTANCE = this;

        this.getServer().getPluginManager().registerEvents(new DevEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Blockader getInstance() {
        return INSTANCE;
    }

    public GameManager gameManager() {
        return this.gameManager;
    }

    public PlayerManager playerManager() {
        return this.playerManager;
    }
}
