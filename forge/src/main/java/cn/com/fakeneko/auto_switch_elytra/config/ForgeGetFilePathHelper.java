package cn.com.fakeneko.auto_switch_elytra.config;

import cn.com.fakeneko.auto_switch_elytra.config.services.IGetFilePathHelper;
import net.minecraftforge.fml.loading.FMLPaths;
import java.io.File;

/**
 * @author fakeneko
 * @date 2024/10/24上午12:10
 * @description
 */
public class ForgeGetFilePathHelper implements IGetFilePathHelper {
    @Override
    public File getFilePath() {
        return FMLPaths.CONFIGDIR.get().toFile();
    }
}
