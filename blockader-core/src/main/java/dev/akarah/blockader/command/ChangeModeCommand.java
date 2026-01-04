package dev.akarah.blockader.command;

import com.mojang.brigadier.context.CommandContext;
import dev.akarah.blockader.Blockader;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

public class ChangeModeCommand {
    public static void register(Commands commands) {
        commands.register(
                Commands.literal("dev").executes(ChangeModeCommand::executeDev).build()
        );
        commands.register(
                Commands.literal("play").executes(ChangeModeCommand::executePlay).build()
        );
    }

    public static int executeDev(CommandContext<CommandSourceStack> ctx) {
        var entity = ctx.getSource().getExecutor();
        if(entity instanceof Player player) {
            Blockader.getInstance().gameManager().sendPlayerToCodeArea(player);
        }
        return 0;
    }

    public static int executePlay(CommandContext<CommandSourceStack> ctx) {
        var entity = ctx.getSource().getExecutor();
        if(entity instanceof Player player) {
            Blockader.getInstance().gameManager().sendPlayerToPlayArea(player);
        }
        return 0;
    }
}
