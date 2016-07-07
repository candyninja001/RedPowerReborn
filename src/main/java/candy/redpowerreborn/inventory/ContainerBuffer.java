package candy.redpowerreborn.inventory;

import candy.redpowerreborn.tileentity.TileEntityBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBuffer extends Container {
	protected TileEntityBuffer tileEntityBuffer;
	private int selectionMode;
	private int selectedSlot;
	private int activeSlots;

	//TODO
	public ContainerBuffer(InventoryPlayer inventoryPlayer, TileEntityBuffer te) {
		tileEntityBuffer = te;

		// the Slot constructor takes the IInventory and the slot number in that
		// it binds to
		// and the x-y coordinates it resides on-screen
		
		for (int i = 0; i < 5; i++) {//i for column number   j for row number
			for (int j = 0; j < 4; j++) {
				addSlotToContainer(new Slot(tileEntityBuffer, j + i * 4, 44 + i * 18, 18 + j * 18));
			}
		}

		// commonly used vanilla code that adds the player's inventory
		bindPlayerInventory(inventoryPlayer);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 104 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 162));
		}
	}

	@Override
	public void onCraftGuiOpened(ICrafting listener) {
		super.onCraftGuiOpened(listener);
		listener.sendAllWindowProperties(this, this.tileEntityBuffer);
	}

	// code for shift-clicking items into the inventory
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();
			
			if (index < 20) {
				if (!this.mergeItemStack(stack, 20, 56, true)) {
					return null;
				}
			}
			// places it into the tileEntity is possible since its in the player
			// inventory
			else if (!this.mergeItemStack(stack, 0, 20, false)) {
				return null;
			}
			
			if (stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			
			if (stack.stackSize == itemstack.stackSize) {
				return null;
			}
			
			slot.onPickupFromSlot(player, stack);
		}
		
		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tileEntityBuffer.isUseableByPlayer(playerIn);
	}
}
