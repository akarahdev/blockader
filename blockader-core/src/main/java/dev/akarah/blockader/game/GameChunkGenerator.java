package dev.akarah.blockader.game;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class GameChunkGenerator extends ChunkGenerator {
    int chunkCountX;
    int chunkCountZ;
    boolean endless;
    GeneratorMode generatorMode;

    private GameChunkGenerator() {}

    public static GameChunkGenerator ofSized(int chunkCountX, int chunkCountZ, GeneratorMode generatorMode) {
        var gcg = new GameChunkGenerator();
        gcg.chunkCountX = chunkCountX;
        gcg.chunkCountZ = chunkCountZ;
        gcg.generatorMode = generatorMode;
        return gcg;
    }

    public static GameChunkGenerator ofEndless(GeneratorMode generatorMode) {
        var gcg = new GameChunkGenerator();
        gcg.endless = true;
        gcg.generatorMode = generatorMode;
        return gcg;
    }

    public enum GeneratorMode {
        GAME,
        CODE
    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
        boolean canGenerate = false;
        if(this.endless) {
            canGenerate = true;
        }
        if(chunkX >= 0 && chunkZ >= 0 && chunkX < chunkCountX && chunkZ < chunkCountZ) {
            canGenerate = true;
        }
        if(canGenerate) {
            chunkData.setRegion(0, 0, 0, 16, 50, 16, Material.STONE);
            chunkData.setRegion(0, 45, 0, 16, 50, 16, Material.DIRT);
            chunkData.setRegion(0, 50, 0, 16, 51, 16, Material.GRASS_BLOCK);
        }
    }
}
