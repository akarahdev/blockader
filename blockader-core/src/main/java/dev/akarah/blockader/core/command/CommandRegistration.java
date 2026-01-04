package dev.akarah.blockader.core.command;

import io.papermc.paper.command.brigadier.Commands;

public class CommandRegistration {
    public static void bootstrap(Commands commands) {
        CreateGameCommand.register(commands);
        ChangeModeCommand.register(commands);
        DevItemCommands.register(commands);
    }
}
