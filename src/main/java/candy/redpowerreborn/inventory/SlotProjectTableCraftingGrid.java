package candy.redpowerreborn.inventory;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.stats.AchievementList;

public class SlotProjectTableCraftingGrid extends Slot
{
    /** The craft matrix inventory linked to this result slot. */
    private final ContainerProjectTable projectTable;

    public SlotProjectTableCraftingGrid(ContainerProjectTable containerProjectTable, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
    {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.projectTable = containerProjectTable;
    }
    
    @Override
    public void onSlotChange(ItemStack stack1, ItemStack stack2){
    	super.onSlotChange(stack1, stack2);
    	this.projectTable.onCraftMatrixChanged(this.inventory);
    }
    
    @Override
    public void onSlotChanged(){
    	super.onSlotChanged();
    	this.projectTable.onCraftMatrixChanged(this.inventory);
    }
}