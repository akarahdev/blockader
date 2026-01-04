package dev.akarah.blockader.api.registry;

import dev.akarah.blockader.api.scripting.action.CodeBlockType;
import dev.akarah.blockader.api.scripting.value.ValueType;

public class DataRegistries {
    public static DataRegistry<ValueType<?, ?>> VALUE_TYPES = new DataRegistry<>();
    public static DataRegistry<CodeBlockType> CODE_BLOCK_TYPES = new DataRegistry<>();
}
