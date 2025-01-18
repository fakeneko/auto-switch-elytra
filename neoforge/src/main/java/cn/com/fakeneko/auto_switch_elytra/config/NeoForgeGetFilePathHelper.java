package cn.com.fakeneko.auto_switch_elytra.config;

import cn.com.fakeneko.auto_switch_elytra.reflection.IGetFilePathHelper;
import net.neoforged.fml.loading.FMLPaths;
import java.io.File;

/**
 * @author fakeneko
 * @date 2024/11/10下午3:20
 * @description
 */
public class NeoForgeGetFilePathHelper implements IGetFilePathHelper {
    @Override
    public File getFilePath() {
        return FMLPaths.CONFIGDIR.get().toFile();
    }
}
