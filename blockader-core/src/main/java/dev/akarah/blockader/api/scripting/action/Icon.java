package dev.akarah.blockader.api.scripting.action;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.persistence.PersistentDataType;

import javax.xml.crypto.Data;
import java.util.List;

public class Icon {
    ItemType itemType;

    Component name = Component.text("Unnamed").color(TextColor.color(255, 100, 100));
    List<Component> description = List.of();

    private Icon() {}

    public static Icon of(ItemType itemType) {
        var ai = new Icon();
        ai.itemType = itemType;
        return ai;
    }

    public Icon named(Component name) {
        this.name = name;
        return this;
    }

    public Icon description(Component... description) {
        this.description = List.of(description);
        this.description = this.description.stream()
                .map(x -> {
                    var newStyle = x.style().edit(b -> b.decoration(TextDecoration.ITALIC, false));
                    return x.style(newStyle);
                })
                .toList();
        return this;
    }

    public ItemStack toSignEditable(String value) {
        var is = this.itemType.createItemStack();
        is.setData(DataComponentTypes.ITEM_NAME, this.name);
        is.setData(DataComponentTypes.LORE, ItemLore.lore(this.description));
        is.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        is.editPersistentDataContainer(pdc -> {
            pdc.set(
                    new NamespacedKey("blockader", "ui/edits_sign"),
                    PersistentDataType.STRING,
                    value
            );
        });

        return is;
    }
}
