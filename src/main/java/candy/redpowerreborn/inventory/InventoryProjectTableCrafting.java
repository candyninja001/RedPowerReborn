package candy.redpowerreborn.inventory;

import candy.redpowerreborn.item.ItemPlan;
import candy.redpowerreborn.tileentity.TileEntityProjectTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class InventoryProjectTableCrafting extends InventoryCrafting implements IInventory {
	/// ** List of the stacks in the crafting matrix. */
	// private final ItemStack[] stackList;
	/** the width of the crafting inventory */
	private final int inventoryWidth;
	private final int inventoryHeight;
	/** Class containing the callbacks for the events on_GUIClosed and on_CraftMaxtrixChanged. */
	private final Container eventHandler;
	private final World worldObj;
	
	private final int inventorySize;
	private final int craftingSize;
	private final int bufferSize;
	
	private final IInventory inventory;
	
	public InventoryProjectTableCrafting(Container eventHandlerIn, IInventory inventory, int width, int height, World world, int buffer) {
		super(eventHandlerIn, width, height);
		this.worldObj = world;
		// this.inventorySize = 2 * width * height + 1;// 9 crafting, 1 plan, 9 plancrafting
		this.eventHandler = eventHandlerIn;
		this.inventoryWidth = width;
		this.inventoryHeight = height;
		this.inventory = inventory;
		this.craftingSize = width * height;
		this.bufferSize = buffer;
		this.inventorySize = this.craftingSize + this.bufferSize + 1;
	}
	
	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory() {
		//TODO I believe this is only called when searching for stacks to remove when crafting, returning 9 as a test
		return this.craftingSize;
		//return this.inventorySize;
	}
	
	/**
	 * Returns the stack in the given slot.
	 */
	public ItemStack getStackInSlot(int index) {
		// this was test code, not useful any more
//		if(index < this.craftingSize)
//			if(this.inventory.getStackInSlot(index) == null)
//				return this.getStackInPlan(index);
		if (index < this.inventorySize)
			return this.inventory.getStackInSlot(index);
		// if (index == 2 * this.inventoryWidth * this.inventoryHeight + 1)
		// return ItemPlan.getCraftingResult(this.inventory.getStackInSlot(indexPlan)); TODO do not re-enable, only change the actual result if the recipe can be crafted with plan
		// return new ItemStack(Items.diamond);
		return null;
		// return index >= this.inventoryWidth * this.inventoryHeight - 1 ? null : this.inventory.getStackInSlot(index);
	}
	
	// TODO enable if needed
	// public ItemStack getPlan(){
	// return this.getStackInSlot(inventoryWidth*inventoryHeight);
	// }
	//
	// public void setPlan(ItemStack plan){
	// this.setInventorySlotContents(inventoryWidth*inventoryHeight, plan);
	// }
	
	/**
	 * Returns the itemstack in the slot specified (Top left is 0, 0). Args: row, column
	 */
	//TODO note: this is only used when checking for crafting
	// in redpower, crafting returns nothing if the plan stacks cannot be fully supplied
	public ItemStack getStackInRowAndColumn(int row, int column) {
		if (!this.canSupplyPlan())
			return null;
		if (row >= 0 && row < this.inventoryWidth && column >= 0 && column <= this.inventoryHeight) {
			ItemStack stack = this.getStackInSlot(row + column * this.inventoryWidth);
			if (stack == null) {
				//return null;
				// TODO if can supply plan then return plan stack
				return this.getStackInPlan(row + column * this.inventoryWidth);
			}
			return stack;
		}
		return null;
	}
	
	private ItemStack getStackInPlan(int index) {
		ItemStack[] stacksPlan = this.getStacksInPlan();
		if(stacksPlan !=null)
			return stacksPlan[index];
		return null;
				//TODO perform check and return the next valid stack from this.getSupplyStacks()
//				if(stacksPlan[i]!=null){
//					canSupply = false;
//					scan: for(int k = 0; k < this.bufferSize; k++){
//						if(stacksBuffer[k] != null && stacksBuffer[k].stackSize > 0){
//							if(stacksPlan[i].isItemEqualIgnoreDurability(stacksBuffer[k])){
//								stacksBuffer[k].stackSize--;
//								canSupply = true;
//								break scan;
//							}
//						}
//					}
//					if(!canSupply)
//						return false;
//				}
	}

	public boolean canSupplyPlan(){
		ItemStack[] stacksPlan = this.getStacksInPlan();
		ItemStack[] stacksCrafting = this.getCraftingStacks();
		ItemStack[] stacksBuffer = this.getBufferStacksCopy();
		if(stacksPlan == null)
			return true;
		if(stacksPlan.length != stacksCrafting.length)
			return false;//TODO just a small check, probably not needed.
		boolean flag = true;
		boolean canSupply = false;
		for (int i = 0; i < 9; i++){
			if(stacksCrafting[i]==null){
				if(stacksPlan[i]!=null){
					canSupply = false;
					scan: for(int k = 0; k < this.bufferSize; k++){
						if(stacksBuffer[k] != null && stacksBuffer[k].stackSize > 0){
							if(stacksPlan[i].isItemEqualIgnoreDurability(stacksBuffer[k])){
								stacksBuffer[k].stackSize--;
								canSupply = true;
								break scan;
							}
						}
					}
					if(!canSupply)
						return false;
				}
			}
		}
		return true;
	}
	
	
	
	// TODO needs changing if the grafting grid ever changes to be over 3 x 3
	public ItemStack[] getStacksInPlan() {
		ItemStack plan = this.getStackInSlot(this.inventorySize-1);
		if (plan != null) {
			ItemStack[] stacks = ItemPlan.getCraftingStacks(plan);
			if (stacks != null) {
				return stacks;
			}
		}
		return null;
	}
	
//	public boolean canSupplyAllPlanStacks() {
//		ItemStack[] bufferStacks = new ItemStack[this.bufferSize];
//		for (int i = 0; i < this.bufferSize; i++) {
//			bufferStacks[i] = this.getStackInSlot(i + this.craftingSize).copy();
//		}
//		ItemStack plan = this.getStackInSlot(this.inventorySize);
//		if (plan == null)
//			return false;
//		ItemStack[] stacks = ItemPlan.getCraftingStacks(plan);
//		if (stacks != null) {
//			for(ItemStack stack = )
//			return stacks;
//		}
//	}
	
//	public boolean[] getSupplyablityForPlan(){
//		//return true if the supply can supply the plan slot, ignores slots with an actual item
//	}
	
	/**
	 * this returns the 9 stacks that will have items removed, can return the same stack in more than one index
	 */
	
	public ItemStack[] getSupplyStacks(){
		ItemStack[] stacksPlan = this.getStacksInPlan();
		ItemStack[] stacksCrafting = this.getCraftingStacks();
		ItemStack[] stacksBuffer = this.getBufferStacks();
		ItemStack[] stacksBufferCopy = this.getBufferStacksCopy();
		ItemStack[] stacksSupply = this.getCraftingStacks();
		boolean flag = true;
		boolean canSupply = false;
		for (int i = 0; i < 9; i++){
			if(stacksCrafting[i]==null){
				if(stacksPlan != null && stacksPlan[i]!=null){
					scan: for(int k = 0; k < this.bufferSize; k++){
						if(stacksBuffer[k] != null && stacksBufferCopy[k].stackSize > 0){
							if(stacksPlan[i].isItemEqualIgnoreDurability(stacksBuffer[k])){
								stacksBufferCopy[k].stackSize--;
								stacksSupply[i] = stacksBuffer[k];
								break scan;
							}
						}
					}
				}
			}
		}
		return stacksSupply;
	}
	
	public int[] getSupplyIndexes(){
		ItemStack[] stacksPlan = this.getStacksInPlan();
		ItemStack[] stacksCrafting = this.getCraftingStacks();
		ItemStack[] stacksBuffer = this.getBufferStacks();
		ItemStack[] stacksBufferCopy = this.getBufferStacksCopy();
		int[] indexesSupply = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		boolean flag = true;
		boolean canSupply = false;
		for (int i = 0; i < 9; i++){
			if(stacksCrafting[i]==null){
				if(stacksPlan != null && stacksPlan[i]!=null){
					scan: for(int k = 0; k < this.bufferSize; k++){
						if(stacksBuffer[k] != null && stacksBufferCopy[k].stackSize > 0){
							if(stacksPlan[i].isItemEqualIgnoreDurability(stacksBuffer[k])){
								stacksBufferCopy[k].stackSize--;
								indexesSupply[i] = k + this.craftingSize;
								break scan;
							}
						}
					}
				}
			}else{
				if(stacksCrafting[i].stackSize==1){
					scan: for(int k = 0; k < this.bufferSize; k++){
						if(stacksBuffer[k] != null && stacksBufferCopy[k].stackSize > 0){
							if(stacksCrafting[i].isItemEqualIgnoreDurability(stacksBuffer[k])){
								stacksBufferCopy[k].stackSize--;
								indexesSupply[i] = k + this.craftingSize;
								break scan;
							}
						}
					}
				}
			}
		}
		return indexesSupply;
	}
	
	public ItemStack[] getBufferStacks(){
		//return the stack if the buffer can refill the crafting grid after the 
		// TODO ideally reducing the stacksize of these stacks reduces the actual stack size\
		ItemStack[] stacksBuffer = new ItemStack[this.bufferSize];
		for(int i = 0; i < this.bufferSize; i++)
			stacksBuffer[i] = this.getStackInSlot(i + this.craftingSize);
		return stacksBuffer;
	}
	
	public ItemStack[] getBufferStacksCopy(){
		//return the stack if the buffer can refill the crafting grid after the 
		// TODO ideally reducing the stacksize of these stacks reduces the actual stack size\
		ItemStack[] stacksPlan = new ItemStack[this.bufferSize];
		for(int i = 0; i < this.bufferSize; i++)
			if(this.getStackInSlot(i + this.craftingSize)!=null)
				stacksPlan[i] = this.getStackInSlot(i + this.craftingSize).copy();
		return stacksPlan;
	}
	
	/**
	 * Get the name of this object. For players this returns their username
	 */
	public String getName() {
		return "container.crafting";
	}
	
	/**
	 * Returns true if this thing is named
	 */
	public boolean hasCustomName() {
		return false;
	}
	
	/**
	 * Get the formatted ChatComponent that will be used for the sender's username in chat
	 */
	public ITextComponent getDisplayName() {
		return (ITextComponent) (this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
	}
	
	// TODO might be needed, not sure
	// /**
	// * Removes a stack from the given slot and returns it.
	// */
	// public ItemStack removeStackFromSlot(int index)
	// {
	// return ItemStackHelper.func_188383_a(this.stackList, index);
	// }
	
	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	public ItemStack decrStackSize(int index, int count) {
		// code from InventoryCrafting
		// ItemStack itemstack = ItemStackHelper.func_188382_a(this.inventory, index, count);
		//
		// if (itemstack != null)
		// {
		// this.eventHandler.onCraftMatrixChanged(this);
		// }
		//
		// return itemstack;
		if (this.getStackInSlot(index) != null) {
			ItemStack itemstack;
			
			if (this.getStackInSlot(index).stackSize <= count) {
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				this.eventHandler.onCraftMatrixChanged(this);
				return itemstack;
			} else {
				itemstack = this.getStackInSlot(index).splitStack(count);
				
				if (this.getStackInSlot(index).stackSize == 0) {
					this.setInventorySlotContents(index, null);
				}
				
				this.eventHandler.onCraftMatrixChanged(this);
				return itemstack;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < this.inventorySize)
			this.inventory.setInventorySlotContents(index, stack);
		// else
		// this.inventory.setInventorySlotContents(index, stack);
		
		this.eventHandler.onCraftMatrixChanged(this);
	}
	
	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
	 */
	public int getInventoryStackLimit() {
		return 64;
	}
	
	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
	 * hasn't changed and skip it.
	 */
	public void markDirty() {}
	
	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}
	
	public void openInventory(EntityPlayer player) {}
	
	public void closeInventory(EntityPlayer player) {}
	
	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
	 */
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}
	
	public int getField(int id) {
		return 0;
	}
	
	public void setField(int id, int value) {}
	
	public int getFieldCount() {
		return 0;
	}
	
	public void clear() {
		// for (int i = 0; i < this.stackList.length; ++i)
		// {
		// this.stackList[i] = null;
		// }
	}
	
	public int getHeight() {
		return this.inventoryHeight;
	}
	
	public int getWidth() {
		return this.inventoryWidth;
	}
	
	public boolean canPlan() {// TODO Can make this require worldObj instead of as a variable
		return this.getStackInSlot(this.inventorySize-1) != null && this.getStackInSlot(this.inventorySize-1).getItem() instanceof ItemPlan && this.getStackInSlot(this.inventorySize-1).stackSize == 1 && !ItemPlan.isSet(this.getStackInSlot(this.inventorySize-1)) && CraftingManager.getInstance().findMatchingRecipe(this, this.worldObj) != null;
	}
	
	public void recordPlan() {
		if (canPlan())
			ItemPlan.writeToNBT(this.getStackInSlot(this.inventorySize-1), this.getCraftingStacks(), CraftingManager.getInstance().findMatchingRecipe(this, this.worldObj));// TODO fix
	}
	
	private ItemStack[] getCraftingStacks() {
		ItemStack[] stacks = new ItemStack[9];
		for (int i = 0; i < 9; i++) {
			stacks[i] = this.getStackInSlot(i);
		}
		return stacks;
	}
	
	// public void setResult(ItemStack resultIn) {
	// this.result = resultIn;
	// }
	//
	// public boolean isButtonVisible(){
	// return this.buttonVisible;
	// }
}