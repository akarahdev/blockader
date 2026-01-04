package dev.akarah.blockader.core.game.dev;

import dev.akarah.blockader.Blockader;
import dev.akarah.blockader.api.registry.DataRegistries;
import dev.akarah.blockader.api.scripting.action.CodeBlockType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockType;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Optional;

public class CodeEditor {
    public static Optional<CodeBlockType> codeBlockByBlockType(BlockType type) {
        return DataRegistries.CODE_BLOCK_TYPES
                .valueSet()
                .stream()
                .filter(x -> x.blockType().equals(type))
                .findFirst();
    }

    public static void placeCodeBlock(Player player, Location location, CodeBlockType codeBlockType) {
        Bukkit.getGlobalRegionScheduler().run(Blockader.getInstance(), task -> {
            CodeEditor.shiftCodespace(location, new Vector(0, 0, codeBlockType.hasBrackets() ? 4 : 2));
        });
        Bukkit.getGlobalRegionScheduler().run(Blockader.getInstance(), task -> {
            location.getBlock().setBlockData(
                    codeBlockType.blockType().createBlockData()
            );
            if(codeBlockType.hasBrackets()) {
                var open = BlockType.PISTON.createBlockData();
                open.setFacing(BlockFace.SOUTH);
                location.clone().add(0, 0, 1).getBlock().setBlockData(open);
                var close = BlockType.PISTON.createBlockData();
                close.setFacing(BlockFace.NORTH);
                location.clone().add(0, 0, 3).getBlock().setBlockData(close);
            } else {
                location.clone().add(0, 0, 1).getBlock().setBlockData(
                        BlockType.STONE.createBlockData()
                );
            }
            if(codeBlockType.hasParameters()) {
                location.clone().add(0, 1, 0).getBlock().setBlockData(
                        BlockType.BARREL.createBlockData()
                );
            }
            var signPos = location.clone().add(-1, 0, 0);
            var signData = BlockType.DARK_OAK_WALL_SIGN.createBlockData();
            signData.setFacing(BlockFace.WEST);
            location.getWorld().setBlockData(signPos, signData);

            var signState = (Sign) location.getWorld().getBlockState(signPos);
            signState.getSide(Side.FRONT).line(
                    0,
                    Component.text(codeBlockType.name().toUpperCase())
            );
            signState.getSide(Side.FRONT).setColor(DyeColor.WHITE);
            signState.getSide(Side.FRONT).setGlowingText(true);
            signState.update();
        });
    }

    public static void shiftCodespace(Location location, Vector offset) {
        var blockStates = new ArrayList<BlockState>();
        var blockDatas = new ArrayList<BlockData>();
        for(int x = -1; x <= 0; x++) {
            for(int z = 0; z <= 50; z++) {
                for(int y = 0; y <= 1; y++) {
                    var pos = location.clone().add(x, y, z);
                    blockStates.add(pos.getBlock().getState().copy());
                    blockDatas.add(pos.getBlock().getBlockData());
                    pos.getBlock().setBlockData(BlockType.AIR.createBlockData());
                }
            }
        }

        int idx = 0;
        for(int x = -1; x <= 0; x++) {
            for(int z = 0; z <= 50; z++) {
                for(int y = 0; y <= 1; y++) {
                    var pos = location.clone().add(x, y, z).add(offset);
                    pos.getBlock().setBlockData(blockDatas.get(idx));
                    blockStates.get(idx).copy(pos);
                    idx += 1;
                }
            }
        }
    }
}
