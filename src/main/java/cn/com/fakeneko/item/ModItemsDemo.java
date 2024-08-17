package cn.com.fakeneko.item;

import cn.com.fakeneko.AutoSwitchElytra;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


/**
 * @author fakeneko
 * @date 2024/8/17下午1:46
 * @description
 */
public class ModItemsDemo {
    // 注册一个物品

    // 物品ID声明
    public static final Item ICE_ETHER = new Item(new Item.Settings());
    public static final Item FIRE_ETHER = new Item(new Item.Settings());

    // 物品注册函数
    private static void registerItem(String lowercase_item_name, Item item_name) {
        Registry.register(Registries.ITEM, Identifier.of(AutoSwitchElytra.MOD_ID, lowercase_item_name), item_name);
    }

    // 实际物品注册
    public static void onItemInitialize() {
        AutoSwitchElytra.LOGGER.info("Registry Item Start!");
        registerItem("ice_ether", ICE_ETHER);
        registerItem("fire_ether", FIRE_ETHER);
        AutoSwitchElytra.LOGGER.info("Registry Item End!");
    }
}
