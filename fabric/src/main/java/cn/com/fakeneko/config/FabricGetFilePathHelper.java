package cn.com.fakeneko.config;

import cn.com.fakeneko.reflection.IGetFilePathHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class FabricGetFilePathHelper implements IGetFilePathHelper {
    @Override
    public File getFilePath() {
        return FabricLoader.getInstance().getConfigDir().toFile();
    }
}
