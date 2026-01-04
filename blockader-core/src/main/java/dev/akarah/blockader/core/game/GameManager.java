package dev.akarah.blockader.core.game;

import com.google.common.collect.Maps;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class GameManager {
    Map<UUID, LoadedGame> loadedGames = Maps.newHashMap();

    public LoadedGame gameById(UUID uuid) {
        if (loadedGames.containsKey(uuid)) {
            return loadedGames.get(uuid);
        }
        return createById(uuid);
    }

    public Optional<LoadedGame> gameByWorld(World world) {
        return loadedGames.values().stream()
                .filter(plot -> plot.gameWorld().equals(world) || plot.codeWorld().equals(world))
                .findFirst();
    }

    public Optional<LoadedGame> gameByEntity(Entity entity) {
        return gameByWorld(entity.getWorld());
    }

    private LoadedGame createById(UUID uuid) {
        var gameWorld = Bukkit.createWorld(
                WorldCreator.name("worlds/gamearea/" + uuid)
                        .generator(GameChunkGenerator.ofEndless(GameChunkGenerator.GeneratorMode.GAME))
                        .type(WorldType.FLAT)
        );
        assert gameWorld != null;
        applyDefaultGameRules(gameWorld);

        var codeWorld = Bukkit.createWorld(
                WorldCreator.name("worlds/codearea/" + uuid)
                        .generator(GameChunkGenerator.ofSized(2, 5, GameChunkGenerator.GeneratorMode.CODE))
                        .type(WorldType.FLAT)
        );
        assert codeWorld != null;
        applyDefaultGameRules(codeWorld);
        codeWorld.setAutoSave(false);

        var game = new LoadedGame(
                uuid,
                gameWorld,
                codeWorld
        );
        loadedGames.put(uuid, game);

        return game;
    }

    private void applyDefaultGameRules(World world) {
        world.setGameRule(GameRules.RANDOM_TICK_SPEED, 0);
        world.setGameRule(GameRules.ADVANCE_TIME, false);
        world.setGameRule(GameRules.SPAWN_MOBS, false);
        world.setTime(6000);
        world.setClearWeatherDuration(Integer.MAX_VALUE);
    }

    public void sendPlayerToGame(Player player, UUID uuid) {
        var loadedGame = gameById(uuid);
        player.teleportAsync(
                loadedGame.gameWorld().getSpawnLocation()
        );
    }

    public void sendPlayerToPlayArea(Player player) {
        gameByEntity(player).ifPresent(loadedGame -> {
            player.teleportAsync(
                    loadedGame.gameWorld().getSpawnLocation()
            );
        });
    }

    public void sendPlayerToCodeArea(Player player) {
        gameByEntity(player).ifPresent(loadedGame -> {
            player.teleportAsync(
                    loadedGame.codeWorld().getSpawnLocation()
            );
        });
    }
}