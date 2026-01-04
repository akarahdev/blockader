package dev.akarah.blockader.core.command;

import com.mojang.brigadier.context.CommandContext;
import dev.akarah.blockader.Blockader;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CreateGameCommand {
    public static void register(Commands commands) {
        commands.register(
                Commands.literal("creategame").executes(CreateGameCommand::execute).build()
        );
    }

    public static int execute(CommandContext<CommandSourceStack> ctx) {
        var entity = ctx.getSource().getExecutor();
        if(entity instanceof Player player) {
            var game = Blockader.getInstance().gameManager().gameById(UUID.randomUUID());
            Blockader.getInstance().gameManager().sendPlayerToGame(player, game.uuid());
        }
        return 0;
    }
}
