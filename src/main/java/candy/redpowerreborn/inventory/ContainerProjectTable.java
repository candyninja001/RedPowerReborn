
package candy.redpowerreborn.inventory;

import candy.redpowerreborn.item.ItemPlan;
import candy.redpowerreborn.tileentity.TileEntityProjectTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerProjectTable extends Container {
	protected TileEntityProjectTable tileEntityProjectTable;
	
	public InventoryProjectTableCrafting craftMatrix;
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;
	
	private int canCraft;
	
	public ContainerProjectTable(InventoryPlayer inventoryPlayer, TileEntityProjectTable te) {
		this.worldObj = inventoryPlayer.player.worldObj;
		tileEntityProjectTable = te;
		craftMatrix = new InventoryProjectTableCrafting(this, tileEntityProjectTable, 3, 3, this.worldObj, 18);
		
//		ItemStack[] stacks = ((TileEntityProjectTable) tileEntityProjectTable).getCraftingStacks();
//		int k = 0;
//		while (k < 9) {
//			this.craftMatrix.setInventorySlotContents(k, stacks[k]);
//			k++;
//		}
//		this.craftMatrix.setInventorySlotContents(9, this.tileEntityProjectTable.getStackInSlot(27));
		
//		//Crafting result
//		this.addSlotToContainer(new Slot(this.craftMatrix, 19, 143, 36){
//			public boolean canBeHovered() {
//				return false;
//			}
//			
//			public boolean canTakeStack(EntityPlayer playerIn){
//				return false;
//			}
//		});
		// must use custom slot to override the defualt check for crafting slots, otherwise the crafting items would not remove stacks from the buffer.
		this.addSlotToContainer(new SlotProjectTableCrafting(inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 143, 36));
		
		//Crafting grid
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				this.addSlotToContainer(new SlotProjectTableCraftingGrid(this, this.craftMatrix, j + i * 3, 48 + j * 18, 18 + i * 18));
			}
		}
		
		//Plan
		addSlotToContainer(new Slot(this.craftMatrix, 27, 17, 36) {
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemPlan;
			}
			
			public int getSlotStackLimit() {
				return 1;
			}
		});
		
		//Buffer
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new SlotProjectTableCraftingGrid(this, this.craftMatrix, 9 + j + i * 9, 8 + j * 18, 90 + i * 18));
			}
		}
		
		// commonly used vanilla code that adds the player's inventory
		bindPlayerInventory(inventoryPlayer);
		
		this.onCraftMatrixChanged(this.craftMatrix);
	}
	
	/**
	 * Callback for when the crafting matrix is changed.
	 */
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		ItemStack result = CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj);
		this.craftResult.setInventorySlotContents(0, result);
//		for (int i = 0; i < 9; i++)
//			this.tileEntityProjectTable.setInventorySlotContents(i, this.craftMatrix.getStackInSlot(i));
//		this.tileEntityProjectTable.setInventorySlotContents(27, this.craftMatrix.getStackInSlot(9));
		this.tileEntityProjectTable.setField(0, this.craftMatrix.canPlan() ? 1 : 0);
		((TileEntityProjectTable) this.tileEntityProjectTable).setResult(result);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 198));
		}
	}
	
//	@Override
//	public void onCraftGuiOpened(ICrafting listener) {
//		super.onCraftGuiOpened(listener);
//		listener.sendAllWindowProperties(this, this.tileEntityProjectTable);
//	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		int k = 0;
		this.tileEntityProjectTable.markDirty();
	}
	
	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	
	// TODO see what crafting table and chest do
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
			
			if (this.canCraft != this.tileEntityProjectTable.getField(0)) {
				icontainerlistener.sendProgressBarUpdate(this, 0, this.tileEntityProjectTable.getField(0));
			}
		}
		
		this.canCraft = this.tileEntityProjectTable.getField(0);
	}
	
	// TODO see if this is needed
	// @Override
	// @SideOnly(Side.CLIENT)
	// public void updateProgressBar(int id, int data) {
	// this.tileEntityProjectTable.setField(id, data);
	// }
	
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
			if (slot < 29) {
				if (!this.mergeItemStack(stackInSlot, 29, 65, true)) {
					return null;
				}
			}
			// places it into the tileEntity is possible since its in the player
			// inventory
			else if (!this.mergeItemStack(stackInSlot, 11, 29, false)) {
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
		return tileEntityProjectTable.isUseableByPlayer(playerIn);
	}
}
