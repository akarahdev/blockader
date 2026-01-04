package dev.akarah.blockader;

import dev.akarah.blockader.command.CommandRegistration;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

@SuppressWarnings("experimental")
public class Bootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext bootstrapContext) {
        bootstrapContext.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,
                event -> {
                    CommandRegistration.bootstrap(event.registrar());
                }
        );
    }
}
