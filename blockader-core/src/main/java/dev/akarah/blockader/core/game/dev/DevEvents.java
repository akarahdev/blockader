package dev.akarah.blockader.core.game.dev;

import dev.akarah.blockader.api.scripting.ValueType;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import javax.naming.OperationNotSupportedException;

public class DevEvents implements Listener {
    @EventHandler
    @SuppressWarnings("unchecked")
    public void rightClickValue(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            var item = event.getPlayer().getInventory().getItem(EquipmentSlot.HAND);
            ValueType.valueTypeOfItem(item).ifPresent(valueType -> {
                valueType.item().ifPresent(itemConfig -> {
                    try {
                        var data = valueType.createDataFromItemStack(item);
                        var mapped = ((ValueType.ItemConfig<Object>) itemConfig)
                                .editRightClick(event.getPlayer(), data);
                        var newItem = ((ValueType<Object, Object>) valueType)
                                .createItemFromValue(mapped);

                        event.getPlayer().getInventory().setItem(
                                EquipmentSlot.HAND,
                                newItem
                        );
                        event.setCancelled(true);
                    } catch (UnsupportedOperationException ignored) {

                    }
                });
            });
        }
    }

    @EventHandler
    @SuppressWarnings("unchecked")
    public void chatValue(AsyncChatEvent event) {
        var item = event.getPlayer().getInventory().getItem(EquipmentSlot.HAND);
        ValueType.valueTypeOfItem(item).ifPresent(valueType -> {
            valueType.item().ifPresent(itemConfig -> {
                try {
                    var data = valueType.createDataFromItemStack(item);
                    var rawMessage = PlainTextComponentSerializer.plainText().serialize(event.message());
                    var mapped = ((ValueType.ItemConfig<Object>) itemConfig)
                            .editChat(event.getPlayer(), data, rawMessage);
                    var newItem = ((ValueType<Object, Object>) valueType)
                            .createItemFromValue(mapped);

                    event.getPlayer().getInventory().setItem(
                            EquipmentSlot.HAND,
                            newItem
                    );
                    event.setCancelled(true);
                } catch (UnsupportedOperationException ignored) {

                }
            });
        });
    }
}
