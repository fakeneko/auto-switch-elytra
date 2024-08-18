package cn.com.fakeneko.common.util;

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

    public static void equipBestElytra(PlayerEntity player)
    {
        if (player == null)
        {
            return;
        }

        ScreenHandler container = player.currentScreenHandler;

        Predicate<ItemStack> filter = (s) ->  s.getItem() instanceof ElytraItem && ElytraItem.isUsable(s) && s.getDamage() < s.getMaxDamage() - 10;
        int targetSlot = findSlotWithBestItemMatch(container, (testedStack, previousBestMatch) -> {
            if (!filter.test(testedStack)) return false;
            if (!filter.test(previousBestMatch)) return true;
            if (getEnchantmentLevel(testedStack, Enchantments.UNBREAKING) > getEnchantmentLevel(previousBestMatch, Enchantments.UNBREAKING))
            {
                return true;
            }
            if (getEnchantmentLevel(testedStack, Enchantments.UNBREAKING) < getEnchantmentLevel(previousBestMatch, Enchantments.UNBREAKING))
            {
                return false;
            }
            return testedStack.getDamage() <= previousBestMatch.getDamage();
        }, UniformIntProvider.create(9, container.slots.size() - 1));

        if (targetSlot >= 0)
        {
            swapItemToEquipmentSlot(player, EquipmentSlot.CHEST, targetSlot);
        }
    }

    private static int findSlotWithBestItemMatch(ScreenHandler container, ItemPickerTest itemTest, UniformIntProvider... ranges)
    {
        final int max = container.slots.size() - 1;
        ItemStack bestMatch = ItemStack.EMPTY;
        int slotNum = -1;

        for (UniformIntProvider range : ranges)
        {
            int end = Math.min(max, range.getMax());

            for (int slotNumber = range.getMin(); slotNumber <= end; ++slotNumber)
            {
                Slot slot = container.getSlot(slotNumber);

                if (itemTest.isBetterMatch(slot.getStack(), bestMatch))
                {
                    bestMatch = slot.getStack();
                    slotNum = slot.id;
                }
            }
        }

        return slotNum;
    }

    public interface ItemPickerTest
    {
        boolean isBetterMatch(ItemStack testedStack, ItemStack previousBestMatch);
    }

    public static int getEnchantmentLevel(ItemStack stack, RegistryKey<Enchantment> enchantment)
    {
        ItemEnchantmentsComponent enchants = stack.getEnchantments();

        if (!enchants.equals(ItemEnchantmentsComponent.DEFAULT))
        {
            Set<RegistryEntry<Enchantment>> enchantList = enchants.getEnchantments();

            for (RegistryEntry<Enchantment> entry : enchantList)
            {
                if (entry.matchesKey(enchantment))
                {
                    return enchants.getLevel(entry);
                }
            }
        }

        return -1;
    }

    public static void swapItemToEquipmentSlot(PlayerEntity player, EquipmentSlot type, int sourceSlotNumber)
    {
        if (sourceSlotNumber != -1 && player.currentScreenHandler == player.playerScreenHandler)
        {
            int equipmentSlotNumber = getSlotNumberForEquipmentType(type, player);
            swapSlots(player, sourceSlotNumber, equipmentSlotNumber);
        }
    }

    /**
     * Returns the slot number for the given equipment type
     * in the player's inventory container
     */
    private static int getSlotNumberForEquipmentType(EquipmentSlot type, PlayerEntity player)
    {
        return switch (type) {
            case MAINHAND -> player != null ? player.getInventory().selectedSlot + 36 : -1;
            case OFFHAND -> 45;
            case HEAD -> 5;
            case CHEST -> 6;
            case LEGS -> 7;
            case FEET -> 8;
            default -> -1;
        };

    }

    public static void swapSlots(PlayerEntity player, int slotNum, int otherSlot)
    {
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
    public static int findSlotWithItem(ScreenHandler container, ItemStack stackReference, boolean allowHotbar, boolean reverse)
    {
        final int startSlot = reverse ? container.slots.size() - 1 : 0;
        final int endSlot = reverse ? -1 : container.slots.size();
        final int increment = reverse ? -1 : 1;
        final boolean isPlayerInv = container instanceof PlayerScreenHandler;

        for (int slotNum = startSlot; slotNum != endSlot; slotNum += increment)
        {
            Slot slot = container.slots.get(slotNum);

            if ((!isPlayerInv || fi.dy.masa.malilib.util.InventoryUtils.isRegularInventorySlot(slot.id, false)) &&
                    (allowHotbar || !isHotbarSlot(slot.id)) &&
                    fi.dy.masa.malilib.util.InventoryUtils.areStacksEqualIgnoreDurability(slot.getStack(), stackReference))
            {
                return slot.id;
            }
        }

        return -1;
    }

    public static boolean isHotbarSlot(int slot) {
        return slot >= 36 && slot < (36 + PlayerInventory.getHotbarSize());
    }


    public static void swapElytraAndChestPlate(PlayerEntity player)
    {
        if (player == null)
        {
            return;
        }

        ScreenHandler container = player.currentScreenHandler;
        ItemStack currentStack = player.getEquippedStack(EquipmentSlot.CHEST);

        Predicate<ItemStack> stackFilterChestPlate = (s) -> s.getItem() instanceof ArmorItem && ((ArmorItem) s.getItem()).getSlotType() == EquipmentSlot.CHEST;

        if (currentStack.isEmpty() || stackFilterChestPlate.test(currentStack))
        {
            equipBestElytra(player);
        }
        else
        {
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

            if (targetSlot >= 0)
            {
                swapItemToEquipmentSlot(player, EquipmentSlot.CHEST, targetSlot);
            }
        }
    }

    private static double getArmorAndArmorToughnessValue(ItemStack stack, double base, AttributeModifierSlot slot)
    {
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
}
