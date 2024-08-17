package cn.com.fakeneko.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import cn.com.fakeneko.AutoSwitchElytra;

/**
 * @author nocturne
 * @date 2024/8/12 下午6:05
 * @description
 */
public class BlockDemo{

    /**
     * 创建方块
     */
    // hardness 硬度
    // resistance 爆炸抗性
    public static final Block EXAMPLE_BLOCK = new Block(Block.Settings.create().strength(1.0f,4.0f));
    public static final Block ICE_ETHER_ORE = new Block(Block.Settings.create().strength(1.0f,4.0f));

    // 方块注册分两给部分，一个是方块物品，一个是方块本体
    // 将注册方块物品独立出来
    private static void registerBlockItem(String lowercase_item_name, Block block_name) {
        Registry.register(Registries.ITEM, Identifier.of(AutoSwitchElytra.MOD_ID, lowercase_item_name), new BlockItem(block_name, new Item.Settings()));
    }

    // 将注册方块本体独立出来
    private static void registerBlockBlock(String lowercase_item_name, Block block_name) {
        Registry.register(Registries.BLOCK, Identifier.of(AutoSwitchElytra.MOD_ID, lowercase_item_name), block_name);
    }

    // 简单调用，同时注册方块本体和方块物品
    private static void registerBlock(String lowercase_item_name, Block block_name) {
        registerBlockItem(lowercase_item_name, block_name);
        registerBlockBlock(lowercase_item_name, block_name);
    }

    public static void onBlockInitialize() {
        // 注册方块
        AutoSwitchElytra.LOGGER.info("Registry Block Start!");
//        Registry.register(Registries.BLOCK, Identifier.of(AutoSwitchElytra.MOD_ID, "example_block"), EXAMPLE_BLOCK);
//        registerBlockBlock("example_block", EXAMPLE_BLOCK);
//        registerBlockItem("example_block", EXAMPLE_BLOCK);
        registerBlock("example_block", EXAMPLE_BLOCK);
        registerBlock("ice_ether_ore", ICE_ETHER_ORE);
        AutoSwitchElytra.LOGGER.info("Registry Block End!");
    }
}
