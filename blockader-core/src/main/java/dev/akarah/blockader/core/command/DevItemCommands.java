package dev.akarah.blockader.core.command;

import dev.akarah.blockader.api.registry.DataRegistries;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

public class DevItemCommands {
    public static void register(Commands commands) {
        // register values
        var valueRoot = Commands.literal("value");
        for(var key : DataRegistries.VALUE_TYPES.keySet()) {
            System.out.println(key);
            valueRoot.then(
                    Commands.literal(key.toString()).executes(ctx -> {
                        if(ctx.getSource().getExecutor() instanceof Player player) {
                            player.getInventory().addItem(
                                    DataRegistries.VALUE_TYPES.get(key)
                                            .orElseThrow()
                                            .defaultItem()
                            );
                        }
                        return 0;
                    })
            );
        }
        commands.register(valueRoot.build());
    }
}
