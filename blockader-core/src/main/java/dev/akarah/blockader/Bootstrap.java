package dev.akarah.blockader;

import dev.akarah.blockader.core.command.CommandRegistration;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

@SuppressWarnings("experimental")
public class Bootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext bootstrapContext) {
        dev.akarah.blockader.stdlib.Bootstrap.bootstrap(bootstrapContext);

        bootstrapContext.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,
                event -> {
                    CommandRegistration.bootstrap(event.registrar());
                }
        );
    }
}
