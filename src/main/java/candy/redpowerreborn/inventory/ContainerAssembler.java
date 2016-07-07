package candy.redpowerreborn.inventory;

import candy.redpowerreborn.tileentity.TileEntityAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerAssembler extends Container {
	protected TileEntityAssembler tileEntityAssembler;
	private int selectionMode;
	private int selectedSlot;
	private int activeSlots;

	//TODO
	public ContainerAssembler(InventoryPlayer inventoryPlayer, TileEntityAssembler te) {
		tileEntityAssembler = te;

		// the Slot constructor takes the IInventory and the slot number in that
		// it binds to
		// and the x-y coordinates it resides on-screen
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				if(i == 0 && j ==0)
					addSlotToContainer(new Slot(tileEntityAssembler, 0, 8, 18));
				else
					addSlotToContainer(new Slot(tileEntityAssembler, j + i * 8, 8 + j * 18, 18 + i * 18));
			}
		}
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(tileEntityAssembler, j + i * 9 + 16, 8 + j * 18, 63 + i * 18));
			}
		}

		// commonly used vanilla code that adds the player's inventory
		bindPlayerInventory(inventoryPlayer);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 113 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 171));
		}
	}

	@Override
	public void onCraftGuiOpened(ICrafting listener) {
		super.onCraftGuiOpened(listener);
		listener.sendAllWindowProperties(this, this.tileEntityAssembler);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.selectionMode != this.tileEntityAssembler.getField(0)) {
				icrafting.sendProgressBarUpdate(this, 0, this.tileEntityAssembler.getField(0));
			}

			if (this.selectedSlot != this.tileEntityAssembler.getField(1)) {
				icrafting.sendProgressBarUpdate(this, 1, this.tileEntityAssembler.getField(1));
			}
			
			if (this.activeSlots != this.tileEntityAssembler.getField(2)) {
				icrafting.sendProgressBarUpdate(this, 2, this.tileEntityAssembler.getField(2));
			}
		}

		this.selectionMode = this.tileEntityAssembler.getField(0);
		this.selectedSlot = this.tileEntityAssembler.getField(1);
		this.activeSlots = this.tileEntityAssembler.getField(2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileEntityAssembler.setField(id, data);
	}

	// code for shift-clicking items into the inventory
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();
			
			if (index < 34) {
				if (!this.mergeItemStack(stack, 34, 70, true)) {
					return null;
				}
			}
			// places it into the tileEntity is possible since its in the player
			// inventory
			else if (!this.mergeItemStack(stack, 16, 34, false)) {
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
		return tileEntityAssembler.isUseableByPlayer(playerIn);
	}
	
	

}
