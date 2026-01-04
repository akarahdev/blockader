package dev.akarah.blockader.core.game.dev;

import com.google.common.collect.Maps;
import dev.akarah.blockader.Blockader;
import dev.akarah.blockader.api.scripting.value.ItemConfig;
import dev.akarah.blockader.api.scripting.value.ValueType;
import dev.akarah.blockader.core.game.PlayerManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.*;
import org.bukkit.block.sign.Side;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
                        var mapped = ((ItemConfig<Object>) itemConfig)
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
                    var mapped = ((ItemConfig<Object>) itemConfig)
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

    @EventHandler
    public void swapHands(PlayerSwapHandItemsEvent event) {
        var pm = Blockader.getInstance().playerManager();
        var gm = Blockader.getInstance().gameManager();
        if (pm.mode(event.getPlayer()).equals(PlayerManager.Mode.EDIT)) {
            event.setCancelled(true);
            gm.gameByEntity(event.getPlayer()).ifPresent(loadedGame -> {
                if(event.getPlayer().getWorld().equals(loadedGame.codeWorld())) {
                    Blockader.getInstance().gameManager().sendPlayerToBuildArea(event.getPlayer());
                } else if(event.getPlayer().getWorld().equals(loadedGame.gameWorld())) {
                    Blockader.getInstance().gameManager().sendPlayerToCodeArea(event.getPlayer());
                }
            });
        }
    }

    @EventHandler
    public void placeCodeBlock(BlockPlaceEvent event) {
        var pm = Blockader.getInstance().playerManager();
        var gm = Blockader.getInstance().gameManager();
        if (pm.mode(event.getPlayer()).equals(PlayerManager.Mode.EDIT)) {
            gm.gameByEntity(event.getPlayer()).ifPresent(loadedGame -> {
                if(event.getPlayer().getWorld().equals(loadedGame.codeWorld())) {
                    event.setCancelled(true);
                    CodeEditor.codeBlockByBlockType(event.getBlockPlaced().getType().asBlockType()).ifPresent(codeBlockType -> {
                        CodeEditor.placeCodeBlock(event.getPlayer(), event.getBlockPlaced().getLocation(), codeBlockType);
                    });
                }
            });
        }
    }

    Map<UUID, Location> targetedSigns = Maps.newHashMap();

    @EventHandler
    public void editSign(PlayerInteractEvent event) {
        if(!Blockader.getInstance().playerManager().mode(event.getPlayer()).equals(PlayerManager.Mode.EDIT)) {
            return;
        }
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        assert event.getClickedBlock() != null;
        if (!(event.getClickedBlock().getState() instanceof Sign sign)) {
            return;
        }
        var codeBlockPos = event.getClickedBlock().getLocation().clone().add(1, 0, 0);
        targetedSigns.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
        var codeBlockTypeOptional = CodeEditor.codeBlockByBlockType(codeBlockPos.getBlock().getType().asBlockType());
        codeBlockTypeOptional.ifPresent(codeBlockType -> {
            event.setCancelled(true);
            var inv = Bukkit.createInventory(null, 9 * 6);
            for(var action : codeBlockType.actionRegistry().valueSet()) {
                inv.addItem(action.icon().toSignEditable(action.signId()));
            }
            event.getPlayer().openInventory(inv);
        });
    }

    @EventHandler
    public void clickIcon(InventoryClickEvent event) {
        var is = event.getCurrentItem();
        if(is == null) {
            return;
        }
        if(is.getPersistentDataContainer().has(new NamespacedKey("blockader", "ui/edits_sign"))) {
            Bukkit.getGlobalRegionScheduler().run(Blockader.getInstance(), task -> {
                event.getWhoClicked().closeInventory();

            });
            var data = is.getPersistentDataContainer()
                    .get(
                            new NamespacedKey("blockader", "ui/edits_sign"),
                            PersistentDataType.STRING
                    );
            assert data != null;
            var signPos = targetedSigns.get(event.getWhoClicked().getUniqueId());
            var signState = (Sign) signPos.getWorld().getBlockState(signPos);
            signState.getSide(Side.FRONT).line(
                    1,
                    Component.text(data)
            );
            signState.update();
        }
    }
}
