
package candy.redpowerreborn.inventory;

import candy.redpowerreborn.tileentity.TileEntityAlloyFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerAlloyFurnace extends Container {
	protected TileEntityAlloyFurnace tileEntityAlloyFurnace;
	private int alloyFurnaceFuelTime; // furnaceBurnTime
	private int cookTime; // currentItemBurnTime
	private int currentCookTime; // cookTime
	private int totalCookTime;

	public ContainerAlloyFurnace(InventoryPlayer inventoryPlayer, TileEntityAlloyFurnace te) {
		tileEntityAlloyFurnace = te;

		// the Slot constructor takes the IInventory and the slot number in that
		// it binds to
		// and the x-y coordinates it resides on-screen
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				addSlotToContainer(new Slot(tileEntityAlloyFurnace, j + i * 3, 48 + j * 18, 17 + i * 18));
			}
		}
		addSlotToContainer(new Slot(tileEntityAlloyFurnace, 9, 17, 42));
		addSlotToContainer(new Slot(tileEntityAlloyFurnace, 10, 141, 35) {
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});

		// commonly used vanilla code that adds the player's inventory
		bindPlayerInventory(inventoryPlayer);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void onCraftGuiOpened(ICrafting listener) {
		super.onCraftGuiOpened(listener);
		listener.sendAllWindowProperties(this, this.tileEntityAlloyFurnace);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.currentCookTime != this.tileEntityAlloyFurnace.getField(2)) {
				icrafting.sendProgressBarUpdate(this, 2, this.tileEntityAlloyFurnace.getField(2));
			}

			if (this.alloyFurnaceFuelTime != this.tileEntityAlloyFurnace.getField(0)) {
				icrafting.sendProgressBarUpdate(this, 0, this.tileEntityAlloyFurnace.getField(0));
			}

			if (this.cookTime != this.tileEntityAlloyFurnace.getField(1)) {
				icrafting.sendProgressBarUpdate(this, 1, this.tileEntityAlloyFurnace.getField(1));
			}

			if (this.totalCookTime != this.tileEntityAlloyFurnace.getField(3)) {
				icrafting.sendProgressBarUpdate(this, 3, this.tileEntityAlloyFurnace.getField(3));
			}
		}

		this.currentCookTime = this.tileEntityAlloyFurnace.getField(2);
		this.alloyFurnaceFuelTime = this.tileEntityAlloyFurnace.getField(0);
		this.cookTime = this.tileEntityAlloyFurnace.getField(1);
		this.totalCookTime = this.tileEntityAlloyFurnace.getField(3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileEntityAlloyFurnace.setField(id, data);
	}

	// code for shift-clicking items into the inventory
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			// merges the item into player inventory since its in the tileEntity
			if (slot < tileEntityAlloyFurnace.getSizeInventory()) {
				if (!this.mergeItemStack(stackInSlot, tileEntityAlloyFurnace.getSizeInventory(),
						36 + tileEntityAlloyFurnace.getSizeInventory(), true)) {
					return null;
				}
			}
			// places it into the tileEntity is possible since its in the player
			// inventory
			else if (!this.mergeItemStack(stackInSlot, 0, tileEntityAlloyFurnace.getSizeInventory(), false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tileEntityAlloyFurnace.isUseableByPlayer(playerIn);
	}
}
