package dev.akarah.blockader.stdlib;

import dev.akarah.blockader.api.registry.DataRegistries;
import dev.akarah.blockader.stdlib.actions.pevent.LeftClick;
import dev.akarah.blockader.stdlib.codeblocks.PlayerEvent;
import dev.akarah.blockader.stdlib.values.NumberType;
import dev.akarah.blockader.stdlib.values.StringType;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;

public class Bootstrap {
    public static void bootstrap(BootstrapContext bootstrapContext) {
        DataRegistries.VALUE_TYPES.insert(new NumberType());
        DataRegistries.VALUE_TYPES.insert(new StringType());

        var playerEvent = new PlayerEvent();
        playerEvent.actionRegistry().insert(new LeftClick());
        DataRegistries.CODE_BLOCK_TYPES.insert(playerEvent);

    }
}
