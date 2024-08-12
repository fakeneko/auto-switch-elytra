package cn.com.fakeneko.block;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * @author nocturne
 * @date 2024/8/12 下午6:05
 * @description
 */
public class BlockDemo implements ModInitializer {

    /**
     * 创建方块
     */
    public static final Block CREATE_BLOCK = new Block(Block.Settings.create().strength(4.0f));

    @Override
    public void onInitialize() {
        // 注册方块
        Registry.register(Registries.ITEM, Identifier.of("tutorial", "example_block"), new BlockItem(CREATE_BLOCK,
                new Item.Settings()));
    }
}
