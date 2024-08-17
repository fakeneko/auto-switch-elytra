package cn.com.fakeneko.common.feature;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.component.Component;

public class AutoSwitchController {
    // List of tags to ignore when searching for previous chest item
    // 搜索以前胸甲时要忽略的标签列表
    // private static final NbtPathArgument.NbtPath[] BLACKLISTED_TAGS = parseTagPaths("tag.Damage");
//    private static final NbtPathArgumentType.NbtPath[] BLACKLISTED_TAGS = parseTagPaths("tag.Damage");

    @Nullable
    private static CompoundTag previousChestTag;

    // 设置
    public static void setPreviousChestItem(ItemStack item) {
        if (!item.isEmpty()) previousChestTag = getFilteredTag(item);
    }

    // 判断之前是否有胸甲
    public static boolean hasPreviousChestItem() {
        return previousChestTag != null;
    }

    // 清空上一个胸甲信息缓存
    public static void resetPreviousChestItem() {
        previousChestTag = null;
    }

    // 匹配上一件胸甲
    public static boolean matchesPreviousChestItem(ItemStack item) {
        return !item.isEmpty() && getFilteredTag(item).equals(previousChestTag);
    }

    // 函数：获取筛选标签
    // 输入：物品栈（玩家背包）
    // 返回：复合标签（指定物品）
    // CompoundTag，net.minecraft.nbt.CompoundTag，简单理解就是物品NBT标签
    private static CompoundTag getFilteredTag(ItemStack itemStack) {
        // 声明一个空的物品栈
        CompoundTag tag = itemStack.copy(new CompoundTag());

        // Remove all blacklisted tags
        // 删除所有黑名单标签
        for (NbtPathArgumentType.NbtPath path : BLACKLISTED_TAGS) {
            path.remove(tag);
        }
        return tag;
    }

    // 函数：解析标记路径
    //
//    private static NbtPathArgumentType.NbtPath[] parseTagPaths(String... args) {
//        NbtPathArgumentType.NbtPath[] result = new NbtPathArgumentType.NbtPath[args.length];
//        for (int i = 0; i < args.length; i++) {
//            try {
//                // Use data command's path parser for blacklist
//                // 使用数据命令的路径解析器解析黑名单
//                result[i] = NbtPathArgumentType.nbtPath().parse(new StringReader(args[i]));
//            } catch (CommandSyntaxException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return result;
//    }
}
