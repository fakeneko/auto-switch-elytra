package cn.com.fakeneko.common.conf;


import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClothConfigFactory {
    public static Screen create(Screen parent) {
        var builder = me.shedaniel.clothconfig2.api.ConfigBuilder.create()
                .setSavingRunnable(Configuration::save)
                .setTitle(Text.translatable("title.autoelytra.config"))
                .setParentScreen(parent);

        var general = builder.getOrCreateCategory(Text.empty());
        var entryBuilder = builder.entryBuilder();

        // Auto Equip Enabled
        general.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("option.autoelytra.equip.enabled"), Configuration.AUTO_EQUIP_ENABLED.get())
                .setTooltip(Text.translatable("toolip.autoelytra.equip.enabled"))
                .setDefaultValue(Configuration.AUTO_EQUIP_ENABLED.getDefault())
                .setSaveConsumer(Configuration.AUTO_EQUIP_ENABLED::set)
                .build());

        // Auto Equip Toggle Keybind
        general.addEntry(entryBuilder.fillKeybindingField(
                        Text.translatable("key.autoelytra.toggle.equip"), Keybinds.TOGGLE_AUTO_EQUIP)
                .setTooltip(Text.translatable("tooltip.autoelytra.toggle.equip"))
                .build());

        return builder.build();
    }
}
