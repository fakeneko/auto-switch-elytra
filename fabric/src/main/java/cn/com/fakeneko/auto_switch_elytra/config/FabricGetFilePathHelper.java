package cn.com.fakeneko.auto_switch_elytra.config;

import cn.com.fakeneko.auto_switch_elytra.reflection.IGetFilePathHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

/**
 * @author fakeneko
 * @date 2024/11/10下午3:01
 * @description
 */
public class FabricGetFilePathHelper implements IGetFilePathHelper {
    @Override
    public File getFilePath() {
        return FabricLoader.getInstance().getConfigDir().toFile();
    }
}
