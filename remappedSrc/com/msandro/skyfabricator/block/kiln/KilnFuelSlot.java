package com.msandro.skyfabricator.block.kiln;

import com.msandro.skyfabricator.ModRegistry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class KilnFuelSlot extends Slot {
   

   public KilnFuelSlot(Inventory inventory, int invSlot, int xPosition, int yPosition) {
      super(inventory, invSlot, xPosition, yPosition);      
   }

   public boolean canInsert(ItemStack stack) {       
      return stack.getItem() == ModRegistry.CHARCOAL_BLOCK_ITEM.asItem();
   }
}
