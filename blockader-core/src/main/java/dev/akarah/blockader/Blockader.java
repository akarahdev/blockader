package dev.akarah.blockader;

import dev.akarah.blockader.game.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Blockader extends JavaPlugin {
    GameManager gameManager = new GameManager();
    static Blockader INSTANCE;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Blockader.INSTANCE = this;
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
}
