package dev.akarah.blockader.stdlib;

import dev.akarah.blockader.api.registry.DataRegistries;
import dev.akarah.blockader.stdlib.values.NumberType;
import dev.akarah.blockader.stdlib.values.StringType;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;

public class Bootstrap {
    public static void bootstrap(BootstrapContext bootstrapContext) {
        DataRegistries.VALUE_TYPES.insert(new NumberType());
        DataRegistries.VALUE_TYPES.insert(new StringType());
    }
}
