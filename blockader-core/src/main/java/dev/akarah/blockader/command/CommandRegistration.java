package dev.akarah.blockader.command;

import io.papermc.paper.command.brigadier.Commands;

public class CommandRegistration {
    public static void bootstrap(Commands commands) {
        CreateGameCommand.register(commands);
        ChangeModeCommand.register(commands);
    }
}
