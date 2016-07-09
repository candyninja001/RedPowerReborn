package candy.redpowerreborn.tileentity;

import candy.redpowerreborn.block.BlockProjectTable;
import candy.redpowerreborn.block.RedPowerBlocks;
import candy.redpowerreborn.item.ItemPlan;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityProjectTable extends TileEntityLockable implements ITickable, ISidedInventory {
	
	private ItemStack[] projectTableItemStacks = new ItemStack[28];
	
	private static final int[] slotsCrafting = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	private static final int[] slotsBuffer = new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 };
	private static final int[] slotsPlan = new int[] { 27 };
	
	private ItemStack result;
	private boolean buttonVisible;// = false;
	
	private int canCraft;
	
	private String projectTableCustomName;
	
	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory() {
		return this.projectTableItemStacks.length;
	}
	
	/**
	 * Returns the stack in the given slot.
	 */
	public ItemStack getStackInSlot(int index) {
		return this.projectTableItemStacks[index];
	}
	
	/**
	 * Removes up to a specified number of items from an inventory slot and
	 * returns them in a new stack.
	 */
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.projectTableItemStacks, index, count);
	}
	
	/**
	 * Removes a stack from the given slot and returns it.
	 */
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.projectTableItemStacks, index);
	}
	
	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	public void setInventorySlotContents(int index, ItemStack stack) {
		//boolean flag = stack != null && stack.isItemEqual(this.projectTableItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.projectTableItemStacks[index]);
		this.projectTableItemStacks[index] = stack;
		
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
		
		//TODO might need code like this to update button when a plan is entered into the table (or removed but that code goes elsewhere)
//		if (index == 9 && !flag) {
//			this.totalCookTime = this.getCookTime(stack);
//			// this.currentCookTime = 0;
//			this.markDirty();
//		}
	}
	
	/**
	 * Get the name of this object. For players this returns their username
	 */
	public String getName() {
		return this.hasCustomName() ? this.projectTableCustomName : "container.projecttable";
	}
	
	/**
	 * Returns true if this thing is named
	 */
	public boolean hasCustomName() {
		return this.projectTableCustomName != null && !this.projectTableCustomName.isEmpty();
	}
	
	public void setCustomInventoryName(String name) {
		this.projectTableCustomName = name;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.projectTableItemStacks = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");
			
			if (j >= 0 && j < this.projectTableItemStacks.length) {
				this.projectTableItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
		
		if (compound.hasKey("CustomName", 8)) {
			this.projectTableCustomName = compound.getString("CustomName");
		}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < this.projectTableItemStacks.length; ++i) {
			if (this.projectTableItemStacks[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.projectTableItemStacks[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		
		compound.setTag("Items", nbttaglist);
		
		if (this.hasCustomName()) {
			compound.setString("CustomName", this.projectTableCustomName);
		}
		return compound;
	}
	
	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be
	 * 64, possibly will be extended.
	 */
	public int getInventoryStackLimit() {
		return 64;
	}
	
	/**
	 * Like the old updateEntity(), except more generic.
	 */
	//TODO see what crafting table and chest do, project table is a mix
	public void update() {
//		boolean flag = this.isBurning();
//		boolean flag1 = false;
//		
//		if (this.isBurning()) {
//			--this.projectTableFuelTime;
//		}
//		
//		if (!this.worldObj.isRemote) {
//			if (this.isBurning() || !areSlotsEmpty(slotsFuel) && !areSlotsEmpty(slotsCrafting)) {
//				if (!this.isBurning() && this.canSmelt()) {
//					this.cookTime = this.projectTableFuelTime = getItemBurnTime(this.projectTableItemStacks[9]);
//					
//					if (this.isBurning()) {
//						flag1 = true;
//						if (!areSlotsEmpty(slotsFuel)) {
//							--this.projectTableItemStacks[9].stackSize;
//							
//							if (this.projectTableItemStacks[9].stackSize == 0) {
//								this.projectTableItemStacks[9] = projectTableItemStacks[9].getItem().getContainerItem(projectTableItemStacks[9]);
//							}
//						}
//					}
//				}
//				
//				if (this.isBurning() && this.canSmelt()) {
//					++this.currentCookTime;
//					
//					if (this.currentCookTime == this.totalCookTime) {
//						this.currentCookTime = 0;
//						// TODO update this
//						this.totalCookTime = 200; // this.getCookTime(this.projectTableItemStacks[0]);
//						this.smeltItem();
//						flag1 = true;
//					}
//				} else {
//					this.currentCookTime = 0;
//				}
//			} else if (!this.isBurning() && this.currentCookTime > 0) {
//				this.currentCookTime = MathHelper.clamp_int(this.currentCookTime - 2, 0, this.totalCookTime);
//			}
//		}
//		
//		if (flag1 || flag != isBurning()) {
//			this.markDirty();
//			BlockProjectTable.setState(this.isBurning(), this.worldObj, this.pos);
//			worldObj.checkLight(pos);// removes light only
//			
//		}
		
		// if(this.isBurning() != worldObj.getBlockState(pos).getValue(BlockProjectTable.LIT))
		// worldObj.setBlockState(pos, worldObj.getBlockState(pos).withProperty(BlockProjectTable.LIT, isBurning()), 3);
		
	}
	
	/**
	 * Turn one item from the furnace source stack into the appropriate smelted
	 * item in the furnace result stack
	 */
	//TODO make this craftItem?
//	public void smeltItem() {
//		if (this.canSmelt()) {
//			int index = ProjectTableRecipes.instance().getSmeltingIndex(getCraftingStacks());
//			ItemStack itemstack = ProjectTableRecipes.instance().getSmeltingResult(index);
//			
//			if (this.projectTableItemStacks[10] == null) {
//				this.projectTableItemStacks[10] = itemstack.copy();
//			} else if (this.projectTableItemStacks[10].getItem() == itemstack.getItem() && projectTableItemStacks[10].stackSize + itemstack.stackSize <= projectTableItemStacks[10].getMaxStackSize() && projectTableItemStacks[10].stackSize + itemstack.stackSize <= getInventoryStackLimit()) {
//				this.projectTableItemStacks[10].stackSize += itemstack.stackSize; // Forge
//																					// BugFix:
//																					// Results
//																					// may
//																					// have
//																					// multiple
//																					// items
//			}
//			
//			ItemStack[] stacks = ProjectTableRecipes.instance().getSmeltingRequirements(index).clone();
//			int i = 0;
//			int k = 0;
//			int s = 0;
//			int q = 0;
//			while (k < stacks.length) {
//				q = stacks[k].stackSize;
//				while (i < 9) {
//					if (projectTableItemStacks[i] != null && stacks[k].getItem() == projectTableItemStacks[i].getItem()) {
//						if (q >= projectTableItemStacks[i].stackSize) {
//							s = projectTableItemStacks[i].stackSize;
//						} else {
//							s = q;
//						}
//						q -= s;
//						projectTableItemStacks[i].stackSize -= s;
//						if (this.projectTableItemStacks[i] != null && this.projectTableItemStacks[i].stackSize <= 0) {
//							this.projectTableItemStacks[i] = null;
//						}
//					}
//					i++;
//				}
//				i = 0;
//				k++;
//			}
//		}
//	}
	
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 9)
			return false;
//		if (index == 29)//TODO fix this
//			if(stack.getItem() instanceof ItemPlan)
//				return true;
//			else
//				return false;
		return true;
	}
	
	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	public void openInventory(EntityPlayer player) {}
	
	public void closeInventory(EntityPlayer player) {}
	
	public int[] getSlotsForFace(EnumFacing side) {
//		if (side == EnumFacing.UP)
//			return this.slotsCrafting;
//		switch (this.worldObj.getBlockState(pos).getValue(BlockProjectTable.FACING)) {
//			case EAST:
//				if (side == EnumFacing.SOUTH)
//					return this.slotsFuel;
//				if (side == EnumFacing.NORTH)
//					return this.slotsOutput;
//				break;
//			case NORTH:
//				if (side == EnumFacing.EAST)
//					return this.slotsFuel;
//				if (side == EnumFacing.WEST)
//					return this.slotsOutput;
//				break;
//			case SOUTH:
//				if (side == EnumFacing.WEST)
//					return this.slotsFuel;
//				if (side == EnumFacing.EAST)
//					return this.slotsOutput;
//				break;
//			case WEST:
//				if (side == EnumFacing.NORTH)
//					return this.slotsFuel;
//				if (side == EnumFacing.SOUTH)
//					return this.slotsOutput;
//				break;
//			default:
//				break;
//		}
		return this.slotsBuffer;
		// TODO RETURN proper values
		
	}
	
	/**
	 * Returns true if automation can insert the given item in the given slot
	 * from the given side.
	 * 
	 * @param index
	 *            The slot index to test insertion into
	 * @param itemStackIn
	 *            The item to test insertion of
	 * @param direction
	 *            The direction to test insertion from
	 */
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}
	
	/**
	 * Returns true if automation can extract the given item in the given slot
	 * from the given side.
	 * 
	 * @param index
	 *            The slot index to test extraction from
	 * @param stack
	 *            The item to try to extract
	 * @param direction
	 *            The direction to extract from
	 */
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		for (int i : this.getSlotsForFace(direction))
			if (i == index)
				return true;
		return false;
	}
	
	public String getGuiID() {
		return "redpowerreborn:projecttable";
	}
	
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerFurnace(playerInventory, this);
	}
	
	public void clear() {
		for (int i = 0; i < this.projectTableItemStacks.length; ++i) {
			this.projectTableItemStacks[i] = null;
		}
	}
	
	// TODO get the sides set up properly	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing) {
		if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, facing);
		}
		return super.getCapability(capability, facing);
	}
	
	public boolean areSlotsEmpty(int[] slots) {
		for (int i = 0; i < slots.length; ++i)
			if (projectTableItemStacks[slots[i]] != null)
				return false;
		return true;
	}
	
	public ItemStack[] getCraftingStacks() {
		ItemStack[] stacks = new ItemStack[slotsCrafting.length];
		int i = 0;
		while (i < slotsCrafting.length) {
			stacks[i] = projectTableItemStacks[slotsCrafting[i]];
			i++;
		}
		return stacks;
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0:
				return this.canCraft;
			default:
				return 0;
		}
	}
	
	public void setField(int id, int value) {
		switch (id) {
			case 0:
				this.canCraft = value;
				break;
		}
	}
	
	@Override
	public int getFieldCount() {
		return 1;
	}

//	public boolean canPlan(ItemStack plan) {
//		this.buttonVisible = plan != null && plan.getItem() instanceof ItemPlan && plan.stackSize == 1 && !ItemPlan.isSet(plan) && this.result != null;
//		return this.buttonVisible;
//	}
//	
//	public void recordPlan(ItemStack plan) {
//		if (canPlan(plan))
//			ItemPlan.writeToNBT(plan, this.getCraftingStacks(), result);//TODO fix
//	}

	public void setResult(ItemStack resultIn) {
		this.result = resultIn;
	}

	public ItemStack getResult() {
		return this.result.copy();
	}
	
//	public boolean isButtonVisible(){
//		return this.buttonVisible;
//	}
}