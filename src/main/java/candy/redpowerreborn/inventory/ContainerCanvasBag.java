package candy.redpowerreborn.inventory;

import candy.redpowerreborn.item.ItemCanvasBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/*
There's a LOT of code in this one, but read through all of the comments carefully
and it should become clear what everything does.
As a bonus, one of my previous tutorials is included within!
"How to Properly Override Shift-Clicking" is here and better than ever!
At least in my opinion.
If you're like me, and you find no end of frustration trying to figure out which
f-ing index you should use for which slots in your container when overriding
transferStackInSlot, or if your following the original tutorial, then read on.
 */
public class ContainerCanvasBag extends Container {
	/** The Item Inventory for this Container, only needed if you want to reference isUseableByPlayer */
	public final InventoryItem inventory;
	
	/**
	 * Using these will make transferStackInSlot easier to understand and implement
	 * INV_START is the index of the first slot in the Player's Inventory, so our
	 * InventoryItem's number of slots (e.g. 5 slots is array indices 0-4, so start at 5)
	 * Notice how we don't have to remember how many slots we made? We can just use
	 * InventoryItem.INV_SIZE and if we ever change it, the Container updates automatically.
	 */
	// private static final int INV_START = InventoryItem.INV_SIZE, INV_END = INV_START+26,
	// HOTBAR_START = INV_END+1, HOTBAR_END = HOTBAR_START+8;
	
	// If you're planning to add armor slots, put those first like this:
	// ARMOR_START = InventoryItem.INV_SIZE, ARMOR_END = ARMOR_START+3,
	// INV_START = ARMOR_END+1, and then carry on like above.
	
	public ContainerCanvasBag(EntityPlayer par1Player, InventoryPlayer inventoryPlayer, InventoryItem inventoryItem) {
		this.inventory = inventoryItem;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(this.inventory, j + i * 9, 8 + j * 18, 17 + i * 18) {
					//TODO find out if it is better to make a SlotCanvasBag instead of this
					public boolean isItemValid(ItemStack stack) {
						return !(stack.getItem() instanceof ItemCanvasBag);
					}
				});
			}
		}
		
		// If you want, you can add ARMOR SLOTS here as well, but you need to
		// make a public version of SlotArmor. I won't be doing that in this tutorial.
		/*
		 * for (i = 0; i < 4; ++i)
		 * {
		 * // These are the standard positions for survival inventory layout
		 * this.addSlotToContainer(new SlotArmor(this.player, inventoryPlayer, inventoryPlayer.getSizeInventory() - 1 - i, 8, 8 + i * 18, i));
		 * }
		 */
		
		bindPlayerInventory(inventoryPlayer);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			if(i!=inventoryPlayer.currentItem)
				addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
			else
				addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142){
					public boolean canTakeStack(EntityPlayer playerIn) {
						return false;
					}
				});
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// be sure to return the inventory's isUseableByPlayer method
		// if you defined special behavior there:
		return inventory.isUseableByPlayer(entityplayer);
	}
	
	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();
			
			if (index < 27) {
				if (!this.mergeItemStack(stack, 27, 63, true)) {
					return null;
				}
			}
			// places it into the tileEntity is possible since its in the player
			// inventory
			else if (!this.mergeItemStack(stack, 0, 27, false)) {
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
			
			slot.onPickupFromSlot(par1EntityPlayer, stack);
		}
		
		return itemstack;
	}
	
//	@Override
//	public void onCraftGuiOpened(ICrafting listener) {
//		super.onCraftGuiOpened(listener);
//		listener.sendAllWindowProperties(this, this.inventory);
//	}
	
	// /**
	// * You should override this method to prevent the player from moving the stack that
	// * opened the inventory, otherwise if the player moves it, the inventory will not
	// * be able to save properly
	// */
	// @Override
	// public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
	// // this will prevent the player from interacting with the item that opened the inventory:
	// if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) {
	// return null;
	// }
	// return super.slotClick(slot, button, flag, player);
	// }
}