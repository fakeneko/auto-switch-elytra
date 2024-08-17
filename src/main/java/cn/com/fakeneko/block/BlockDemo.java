package cn.com.fakeneko.block;

import net.minecraft.block.Block;
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
    public static final Block EXAMPLE_BLOCK = new Block(Block.Settings.create().strength(4.0f));

    public static void onBlockInitialize() {
        // 注册方块
        AutoSwitchElytra.LOGGER.info("Registry Block Start!");
        Registry.register(Registries.BLOCK, Identifier.of(AutoSwitchElytra.MOD_ID, "example_block"), EXAMPLE_BLOCK);
        AutoSwitchElytra.LOGGER.info("Registry Block End!");
    }
}
