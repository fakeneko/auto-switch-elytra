package cn.com.fakeneko.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.Set;
import java.util.function.Predicate;

public class InventoryUtils {

    // 装备最恰当的鞘翅
    public static void equipBestElytra(PlayerEntity player) {
        // 如果玩家为空，或者玩家的当前容器为空，则直接返回
        if (player == null) {
            return;
        }

        ScreenHandler container = player.currentScreenHandler;

        Predicate<ItemStack> filter = (s) ->  s.getItem() instanceof ElytraItem && ElytraItem.isUsable(s) && s.getDamage() < s.getMaxDamage() - 10;
        int targetSlot = findSlotWithBestItemMatch(container, (testedStack, previousBestMatch) -> {
            if (!filter.test(testedStack)) return false;
            if (!filter.test(previousBestMatch)) return true;
            if (getEnchantmentLevel(testedStack, Enchantments.UNBREAKING) > getEnchantmentLevel(previousBestMatch, Enchantments.UNBREAKING)) {
                return true;
            }
            if (getEnchantmentLevel(testedStack, Enchantments.UNBREAKING) < getEnchantmentLevel(previousBestMatch, Enchantments.UNBREAKING)) {
                return false;
            }
            return testedStack.getDamage() <= previousBestMatch.getDamage();
        }, UniformIntProvider.create(9, container.slots.size() - 1));

        if (targetSlot >= 0) {
            swapItemToEquipmentSlot(player, EquipmentSlot.CHEST, targetSlot);
        }
    }

    // 找到最恰当的物品
    private static int findSlotWithBestItemMatch(ScreenHandler container, ItemPickerTest itemTest, UniformIntProvider... ranges) {
        final int max = container.slots.size() - 1;
        ItemStack bestMatch = ItemStack.EMPTY;
        int slotNum = -1;

        for (UniformIntProvider range : ranges) {
            int end = Math.min(max, range.getMax());
            for (int slotNumber = range.getMin(); slotNumber <= end; ++slotNumber) {
                Slot slot = container.getSlot(slotNumber);
                if (itemTest.isBetterMatch(slot.getStack(), bestMatch)) {
                    bestMatch = slot.getStack();
                    slotNum = slot.id;
                }
            }
        }
        return slotNum;
    }

    // 物品选择器测试
    public interface ItemPickerTest {
        boolean isBetterMatch(ItemStack testedStack, ItemStack previousBestMatch);
    }

    // 获取附魔等级
    // 传入物品，何种附魔
    public static int getEnchantmentLevel(ItemStack stack, RegistryKey<Enchantment> enchantment) {
        // 获取物品附魔抽象类
        ItemEnchantmentsComponent enchants = stack.getEnchantments();
        // 如果物品附魔不为空，并且物品附魔不为默认值，则获取附魔等级
        if (!enchants.equals(ItemEnchantmentsComponent.DEFAULT)) {
            // 获取所有附魔
            Set<RegistryEntry<Enchantment>> enchantList = enchants.getEnchantments();
            // 遍历所有附魔，返回匹配的附魔等级
            for (RegistryEntry<Enchantment> entry : enchantList) {
                if (entry.matchesKey(enchantment)) {
                    return enchants.getLevel(entry);
                }
            }
        }
        return -1;
    }

    // 交换物品栏位到装备栏位
    public static void swapItemToEquipmentSlot(PlayerEntity player, EquipmentSlot type, int sourceSlotNumber) {
        // 如果玩家为空，或者玩家的当前容器为空，或者玩家的当前屏幕为空，则直接返回
        if (sourceSlotNumber != -1 && player.currentScreenHandler == player.playerScreenHandler) {
            int equipmentSlotNumber = getSlotNumberForEquipmentType(type, player);
            swapSlots(player, sourceSlotNumber, equipmentSlotNumber);
        }
    }

    /**
     * Returns the slot number for the given equipment type
     * in the player's inventory container
     */
    // 获取玩家背包容器中的指定装备栏位数字
    private static int getSlotNumberForEquipmentType(EquipmentSlot type, PlayerEntity player) {
        return switch (type) {
            // 主手
            case MAINHAND -> player != null ? player.getInventory().selectedSlot + 36 : -1;
            // 副手
            case OFFHAND -> 45;
            // 头盔
            case HEAD -> 5;
            // 胸甲
            case CHEST -> 6;
            // 护腿
            case LEGS -> 7;
            // 靴子
            case FEET -> 8;
            // 其他
            default -> -1;
        };

    }

    // 交换两个物品栏位
    // 传入玩家，源栏位，目标栏位
    public static void swapSlots(PlayerEntity player, int slotNum, int otherSlot) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ScreenHandler container = player.currentScreenHandler;
        if (mc.interactionManager != null) {
            mc.interactionManager.clickSlot(container.syncId, slotNum, 0, SlotActionType.SWAP, player);
            mc.interactionManager.clickSlot(container.syncId, otherSlot, 0, SlotActionType.SWAP, player);
            mc.interactionManager.clickSlot(container.syncId, slotNum, 0, SlotActionType.SWAP, player);
        }
    }

    /**
     *
     * Finds a slot with an identical item than <b>stackReference</b>, ignoring the durability
     * of damageable items. Does not allow crafting or armor slots or the offhand slot
     * in the ContainerPlayer container.
     * @return the slot number, or -1 if none were found
     */
    // 找到与参考物品相同的物品栏位
    // 传入如下
    // @param1 当前容器
    // @param2 参考物品
    // @param3 是否允许热键栏位
    // @param4 是否反向查找
    // @return 返回查找到的物品栏位，如果没有找到则返回-1
    public static int findSlotWithItem(ScreenHandler container, ItemStack stackReference, boolean allowHotbar, boolean reverse) {
        // 起始值，正序为0，反序为物品栏最后一个
        final int startSlot = reverse ? container.slots.size() - 1 : 0;
        // 结束值，正式为物品栏最后一个，反序为-1
        final int endSlot = reverse ? -1 : container.slots.size();
        // 自增，正序则为1，反序为-1
        final int increment = reverse ? -1 : 1;
        // 是否为玩家背包容器
        final boolean isPlayerInv = container instanceof PlayerScreenHandler;

        // 遍历物品栏
        for (int slotNum = startSlot; slotNum != endSlot; slotNum += increment) {
            Slot slot = container.slots.get(slotNum);

            // 如果是玩家背包容器，并且不是玩家背包栏位，或者是热键栏位，则直接跳过
            if ((!isPlayerInv || isRegularInventorySlot(slot.id, false)) &&
                    (allowHotbar || !isHotbarSlot(slot.id)) &&
                    areStacksEqualIgnoreDurability(slot.getStack(), stackReference))
            {
                return slot.id;
            }
        }

        return -1;
    }

    // 判断是否为热键栏位
    public static boolean isHotbarSlot(int slot) {
        return slot >= 36 && slot < (36 + PlayerInventory.getHotbarSize());
    }

    // 交换鞘翅和胸甲
    public static void swapElytraAndChestPlate(PlayerEntity player) {
        // 如果玩家为空，或者玩家的当前容器为空，则直接返回
        if (player == null) {
            return;
        }
        // 获取玩家当前容器
        ScreenHandler container = player.currentScreenHandler;
        // 获取玩家当前穿戴的胸甲
        ItemStack currentStack = player.getEquippedStack(EquipmentSlot.CHEST);

        Predicate<ItemStack> stackFilterChestPlate = (s) -> s.getItem() instanceof ArmorItem && ((ArmorItem) s.getItem()).getSlotType() == EquipmentSlot.CHEST;

        // 如果当前装备的胸甲时空，直接装备鞘翅
        if (currentStack.isEmpty() || stackFilterChestPlate.test(currentStack)) {
            equipBestElytra(player);
        }
        else {
            Predicate<ItemStack> finalFilter = (s) -> stackFilterChestPlate.test(s) && s.getDamage() < s.getMaxDamage() - 10;

            int targetSlot = findSlotWithBestItemMatch(container, (testedStack, previousBestMatch) -> {
                if (!finalFilter.test(testedStack)) return false;
                if (!finalFilter.test(previousBestMatch)) return true;
                if (getArmorAndArmorToughnessValue(previousBestMatch, 1, AttributeModifierSlot.CHEST) < getArmorAndArmorToughnessValue(testedStack, 1, AttributeModifierSlot.CHEST))
                {
                    return true;
                }
                if (getArmorAndArmorToughnessValue(previousBestMatch, 1, AttributeModifierSlot.CHEST) > getArmorAndArmorToughnessValue(testedStack, 1, AttributeModifierSlot.CHEST))
                {
                    return false;
                }
                return getEnchantmentLevel(previousBestMatch, Enchantments.PROTECTION) <= getEnchantmentLevel(testedStack, Enchantments.PROTECTION);
            }, UniformIntProvider.create(9, container.slots.size() - 1));

            if (targetSlot >= 0) {
                swapItemToEquipmentSlot(player, EquipmentSlot.CHEST, targetSlot);
            }
        }
    }

    // 获取护甲和护甲坚韧值
    private static double getArmorAndArmorToughnessValue(ItemStack stack, double base, AttributeModifierSlot slot) {
        final double[] total = {base};
        stack.applyAttributeModifier(slot, (entry, modifier) -> {
            if (entry.getKey().orElseThrow() == EntityAttributes.GENERIC_ARMOR
                    || entry.getKey().orElseThrow() == EntityAttributes.GENERIC_ARMOR_TOUGHNESS)
            {
                switch (modifier.operation())
                {
                    case ADD_VALUE:
                        total[0] += modifier.value();
                        break;
                    case ADD_MULTIPLIED_BASE:
                        total[0] += modifier.value() * base;
                        break;
                    case ADD_MULTIPLIED_TOTAL:
                        total[0] += modifier.value() * total[0];
                        break;
                    default:
                        throw new MatchException(null, null);
                }
            }
        });

        return total[0];
    }

    /**
     * Assuming that the slot is from the ContainerPlayer container,
     * returns whether the given slot number is one of the regular inventory slots.
     * This means that the crafting slots and armor slots are not valid.
     * @param slotNumber
     * @param allowOffhand
     * @return boolean
     */
    // 传入当前栏位，判断是否在副手或者在背包
    public static boolean isRegularInventorySlot(int slotNumber, boolean allowOffhand) {
        return slotNumber > 8 && (allowOffhand || slotNumber < 45);
    }

    /**
     * @return true if the stacks are identical, but ignoring the stack size,
     * and if the item is damageable, then ignoring the damage too.
     */
    // 判断两个物品是否相同，忽略数量和耐久度
    public static boolean areStacksEqualIgnoreDurability(ItemStack stack1, ItemStack stack2) {
        ItemStack ref = stack1.copy();
        ItemStack check = stack2.copy();

        // It's a little hacky, but it works.
        // 这里是一个小技巧，但是它有效
        // 将数量设置为1，然后比较是否相同
        ref.setCount(1);
        check.setCount(1);

        // 如果物品是可以被损坏的，并且物品已经损坏，则将耐久度设置为0
        if (ref.isDamageable() && ref.isDamaged()) {
            ref.setDamage(0);
        }
        if (check.isDamageable() && check.isDamaged()) {
            check.setDamage(0);
        }

        // 比较是否相同。调用Mojang的方法
        return ItemStack.areItemsAndComponentsEqual(ref, check);
    }
}
