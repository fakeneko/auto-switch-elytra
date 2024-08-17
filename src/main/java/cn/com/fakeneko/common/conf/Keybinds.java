package cn.com.fakeneko.common.conf;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;


import java.util.HashMap;
import java.util.Map;


public class Keybinds {
    protected static final Map<KeyBinding, Runnable> KEYBINDS = new HashMap<>();
    public static final KeyBinding TOGGLE_AUTO_EQUIP = registerKeybind(
            new KeyBinding("key.autoelytra.toggle.equip", InputUtil.Type.KEYSYM, -1, KeyBinding.INVENTORY_CATEGORY),
            () -> {
                if (MinecraftClient.getInstance().currentScreen != null) return; // Only allow when no screen is open

                boolean enabling = !Configuration.AUTO_EQUIP_ENABLED.get();

                Configuration.AUTO_EQUIP_ENABLED.set(enabling);

                Text message;
                message = Text.translatable("message.autoelytra.equip.enabled");
//                if (enabling) {
//                    AutoEquipController.resetPreviousChestItem();
//                    message = Text.translatable("message.autoelytra.equip.enabled"));
//                } else message = Text.translatable("message.autoelytra.equip.disabled"));

//                AutoElytra.sendMessage(message);
                Configuration.save();
            });

    private static KeyBinding registerKeybind(KeyBinding mapping, Runnable action) {
        KEYBINDS.put(mapping, action);
        return mapping;
    }

//    @ExpectPlatform
//    public static void setup() {
//        throw new AssertionError();
//    }
}