package cn.com.fakeneko.config;

import cn.com.fakeneko.reflection.IGetFilePathHelper;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;

public class NeoForgeGetFilePathHelper implements IGetFilePathHelper {
    @Override
    public File getFilePath() {
        return FMLPaths.CONFIGDIR.get().toFile();
    }
}
