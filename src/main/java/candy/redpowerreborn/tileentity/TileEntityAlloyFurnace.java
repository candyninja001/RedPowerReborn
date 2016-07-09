package candy.redpowerreborn.tileentity;

import candy.redpowerreborn.block.BlockAlloyFurnace;
import candy.redpowerreborn.block.RedPowerBlocks;
import candy.redpowerreborn.inventory.ContainerAlloyFurnace;
import candy.redpowerreborn.item.crafting.AlloyFurnaceRecipes;
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
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityAlloyFurnace extends TileEntityLockable implements ITickable, ISidedInventory {
	/** The Slot for the fuel */
	private static final int[] slotsFuel = new int[] { 9 };
	/**
	 * The ItemStacks that hold the items currently being used in the furnace
	 */
	private static final int[] slotsCrafting = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	/**
	 * The ItemStacks that hold the items currently being used in the furnace
	 */
	private static final int[] slotsOutput = new int[] { 10 };
	/**
	 * The ItemStacks that hold the items currently being used in the furnace
	 */
	private ItemStack[] alloyFurnaceItemStacks = new ItemStack[11];
	/** The number of ticks that the alloy furnace will continue to burn for */
	private int alloyFurnaceFuelTime; // furnaceBurnTime
	/**
	 * The number of ticks that a fresh copy of the currently-burning item would
	 * keep the furnace burning for
	 */
	private int cookTime; // currentItemBurnTime
	private int currentCookTime; // cookTime
	private int totalCookTime;
	private String alloyFurnaceCustomName;
	
	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory() {
		return this.alloyFurnaceItemStacks.length;
	}
	
	/**
	 * Returns the stack in the given slot.
	 */
	public ItemStack getStackInSlot(int index) {
		return this.alloyFurnaceItemStacks[index];
	}
	
	/**
	 * Removes up to a specified number of items from an inventory slot and
	 * returns them in a new stack.
	 */
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.alloyFurnaceItemStacks, index, count);
	}
	
	/**
	 * Removes a stack from the given slot and returns it.
	 */
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.alloyFurnaceItemStacks, index);
	}
	
	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean flag = stack != null && stack.isItemEqual(this.alloyFurnaceItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.alloyFurnaceItemStacks[index]);
		this.alloyFurnaceItemStacks[index] = stack;
		
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
		
		if (index == 9 && !flag) {
			this.totalCookTime = this.getCookTime(stack);
			// this.currentCookTime = 0;
			this.markDirty();
		}
	}
	
	/**
	 * Get the name of this object. For players this returns their username
	 */
	public String getName() {
		return this.hasCustomName() ? this.alloyFurnaceCustomName : "container.alloyfurnace";
	}
	
	/**
	 * Returns true if this thing is named
	 */
	public boolean hasCustomName() {
		return this.alloyFurnaceCustomName != null && !this.alloyFurnaceCustomName.isEmpty();
	}
	
	public void setCustomInventoryName(String name) {
		this.alloyFurnaceCustomName = name;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.alloyFurnaceItemStacks = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");
			
			if (j >= 0 && j < this.alloyFurnaceItemStacks.length) {
				this.alloyFurnaceItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
		
		this.alloyFurnaceFuelTime = compound.getInteger("FuelTime");
		this.currentCookTime = compound.getInteger("CookTimeCurrent");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.cookTime = getItemBurnTime(this.alloyFurnaceItemStacks[1]);
		
		if (compound.hasKey("CustomName", 8)) {
			this.alloyFurnaceCustomName = compound.getString("CustomName");
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		compound.setInteger("FuelTime", this.alloyFurnaceFuelTime);
		compound.setInteger("CookTimeCurrent", this.currentCookTime);
		compound.setInteger("CookTimeTotal", this.totalCookTime);
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < this.alloyFurnaceItemStacks.length; ++i) {
			if (this.alloyFurnaceItemStacks[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.alloyFurnaceItemStacks[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		
		compound.setTag("Items", nbttaglist);
		
		if (this.hasCustomName()) {
			compound.setString("CustomName", this.alloyFurnaceCustomName);
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
	 * Furnace isBurning
	 */
	public boolean isBurning() {
		return this.alloyFurnaceFuelTime > 0;
	}
	
	/**
	 * Time left for the fuel burning
	 */
	public int getFuelTime() {
		return alloyFurnaceFuelTime;
	}
	
	// @SideOnly(Side.CLIENT)
	// public static boolean isBurning(IInventory p_174903_0_)
	// {
	// return p_174903_0_.getField(0) > 0;
	// }
	
	/**
	 * Like the old updateEntity(), except more generic.
	 */
	public void update() {
		boolean flag = this.isBurning();
		boolean flag1 = false;
		
		if (this.isBurning()) {
			--this.alloyFurnaceFuelTime;
		}
		
		if (!this.worldObj.isRemote) {
			if (this.isBurning() || !areSlotsEmpty(slotsFuel) && !areSlotsEmpty(slotsCrafting)) {
				if (!this.isBurning() && this.canSmelt()) {
					this.cookTime = this.alloyFurnaceFuelTime = getItemBurnTime(this.alloyFurnaceItemStacks[9]);
					
					if (this.isBurning()) {
						flag1 = true;
						if (!areSlotsEmpty(slotsFuel)) {
							--this.alloyFurnaceItemStacks[9].stackSize;
							
							if (this.alloyFurnaceItemStacks[9].stackSize == 0) {
								this.alloyFurnaceItemStacks[9] = alloyFurnaceItemStacks[9].getItem().getContainerItem(alloyFurnaceItemStacks[9]);
							}
						}
					}
				}
				
				if (this.isBurning() && this.canSmelt()) {
					++this.currentCookTime;
					
					if (this.currentCookTime == this.totalCookTime) {
						this.currentCookTime = 0;
						// TODO update this
						this.totalCookTime = 200; // this.getCookTime(this.alloyFurnaceItemStacks[0]);
						this.smeltItem();
						flag1 = true;
					}
				} else {
					this.currentCookTime = 0;
				}
			} else if (!this.isBurning() && this.currentCookTime > 0) {
				this.currentCookTime = MathHelper.clamp_int(this.currentCookTime - 2, 0, this.totalCookTime);
			}
		}
		
		if (flag1 || flag != isBurning()) {
			this.markDirty();
			BlockAlloyFurnace.setState(this.isBurning(), this.worldObj, this.pos);
			worldObj.checkLight(pos);// removes light only
			
		}
		
		// if(this.isBurning() != worldObj.getBlockState(pos).getValue(BlockAlloyFurnace.LIT))
		// worldObj.setBlockState(pos, worldObj.getBlockState(pos).withProperty(BlockAlloyFurnace.LIT, isBurning()), 3);
		
	}
	
	public int getCookTime(ItemStack stack) {
		return 200;
	}
	
	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item,
	 * destination stack isn't full, etc.
	 */
	private boolean canSmelt() {
		// if (this.alloyFurnaceItemStacks[9] == null) {
		// return false;
		// } else {
		int index = AlloyFurnaceRecipes.instance().getSmeltingIndex(getCraftingStacks());
		ItemStack itemstack = AlloyFurnaceRecipes.instance().getSmeltingResult(index);
		if (index == -1)
			return false;
		if (itemstack == null)
			return false;
		if (this.alloyFurnaceItemStacks[10] == null)
			return true;
		if (!this.alloyFurnaceItemStacks[10].isItemEqual(itemstack))
			return false;
		int result = alloyFurnaceItemStacks[10].stackSize + itemstack.stackSize;
		return result <= getInventoryStackLimit() && result <= this.alloyFurnaceItemStacks[10].getMaxStackSize(); // Forge
																													// BugFix:
																													// Make
																													// it
																													// respect
																													// stack
																													// sizes
																													// properly.
		// }
	}
	
	/**
	 * Turn one item from the furnace source stack into the appropriate smelted
	 * item in the furnace result stack
	 */
	public void smeltItem() {
		if (this.canSmelt()) {
			int index = AlloyFurnaceRecipes.instance().getSmeltingIndex(getCraftingStacks());
			ItemStack itemstack = AlloyFurnaceRecipes.instance().getSmeltingResult(index);
			
			if (this.alloyFurnaceItemStacks[10] == null) {
				this.alloyFurnaceItemStacks[10] = itemstack.copy();
			} else if (this.alloyFurnaceItemStacks[10].getItem() == itemstack.getItem() && alloyFurnaceItemStacks[10].stackSize + itemstack.stackSize <= alloyFurnaceItemStacks[10].getMaxStackSize() && alloyFurnaceItemStacks[10].stackSize + itemstack.stackSize <= getInventoryStackLimit()) {
				this.alloyFurnaceItemStacks[10].stackSize += itemstack.stackSize; // Forge
																					// BugFix:
																					// Results
																					// may
																					// have
																					// multiple
																					// items
			}
			
			ItemStack[] stacks = AlloyFurnaceRecipes.instance().getSmeltingRequirements(index).clone();
			int i = 0;
			int k = 0;
			int s = 0;
			int q = 0;
			while (k < stacks.length) {
				q = stacks[k].stackSize;
				while (i < 9) {
					if (alloyFurnaceItemStacks[i] != null && stacks[k].getItem() == alloyFurnaceItemStacks[i].getItem()) {
						if (q >= alloyFurnaceItemStacks[i].stackSize) {
							s = alloyFurnaceItemStacks[i].stackSize;
						} else {
							s = q;
						}
						q -= s;
						alloyFurnaceItemStacks[i].stackSize -= s;
						if (this.alloyFurnaceItemStacks[i] != null && this.alloyFurnaceItemStacks[i].stackSize <= 0) {
							this.alloyFurnaceItemStacks[i] = null;
						}
					}
					i++;
				}
				i = 0;
				k++;
			}
		}
	}
	
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 10) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns the number of ticks that the supplied fuel item will keep the
	 * furnace burning, or 0 if the item isn't fuel
	 */
	public static int getItemBurnTime(ItemStack p_145952_0_) {
		return TileEntityFurnace.getItemBurnTime(p_145952_0_);
//		if (p_145952_0_ == null) {
//			return 0;
//		} else {
//			Item item = p_145952_0_.getItem();
//			
//			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
//				Block block = Block.getBlockFromItem(item);
//				
//				if (block == Blocks.wooden_slab) {
//					return 150;
//				}
//				
//				if (block.getDefaultState().getMaterial() == Material.wood) {
//					return 300;
//				}
//				
//				if (block == Blocks.coal_block) {
//					return 16000;
//				}
//			}
//			
//			if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD"))
//				return 200;
//			if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD"))
//				return 200;
//			if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD"))
//				return 200;
//			if (item == Items.stick)
//				return 100;
//			if (item == Items.coal)
//				return 1600;
//			if (item == Items.lava_bucket)
//				return 20000;
//			if (item == Item.getItemFromBlock(Blocks.sapling))
//				return 100;
//			if (item == Items.blaze_rod)
//				return 2400;
//			return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(p_145952_0_);
//		}
	}
	
	public static boolean isItemFuel(ItemStack p_145954_0_) {
		/**
		 * Returns the number of ticks that the supplied fuel item will keep the
		 * furnace burning, or 0 if the item isn't fuel
		 */
		return getItemBurnTime(p_145954_0_) > 0;
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
		if (side == EnumFacing.UP)
			return this.slotsCrafting;
		switch (this.worldObj.getBlockState(pos).getValue(BlockAlloyFurnace.FACING)) {
			case EAST:
				if (side == EnumFacing.SOUTH)
					return this.slotsFuel;
				if (side == EnumFacing.NORTH)
					return this.slotsOutput;
				break;
			case NORTH:
				if (side == EnumFacing.EAST)
					return this.slotsFuel;
				if (side == EnumFacing.WEST)
					return this.slotsOutput;
				break;
			case SOUTH:
				if (side == EnumFacing.WEST)
					return this.slotsFuel;
				if (side == EnumFacing.EAST)
					return this.slotsOutput;
				break;
			case WEST:
				if (side == EnumFacing.NORTH)
					return this.slotsFuel;
				if (side == EnumFacing.SOUTH)
					return this.slotsOutput;
				break;
			default:
				break;
		}
		int[] i = {};
		return i;
		// TODO RETURN proper values
		// return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP
		// ? slotsTop : slotsSides);
		
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
		return "redpowerreborn:alloyfurnace";
	}
	
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerAlloyFurnace(playerInventory, this);
	}
	
	public int getField(int id) {
		switch (id) {
			case 0:
				return this.alloyFurnaceFuelTime;
			case 1:
				return this.cookTime;
			case 2:
				return this.currentCookTime;
			case 3:
				return this.totalCookTime;
			default:
				return 0;
		}
	}
	
	public void setField(int id, int value) {
		switch (id) {
			case 0:
				this.alloyFurnaceFuelTime = value;
				break;
			case 1:
				this.cookTime = value;
				break;
			case 2:
				this.currentCookTime = value;
				break;
			case 3:
				this.totalCookTime = value;
		}
	}
	
	// TODO Find out what this does
	public int getFieldCount() {
		return 4;
	}
	
	public void clear() {
		for (int i = 0; i < this.alloyFurnaceItemStacks.length; ++i) {
			this.alloyFurnaceItemStacks[i] = null;
		}
	}
	
	// TODO get the sides set up properly	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing) {
		if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
//			if (facing == EnumFacing.UP)
//				return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, EnumFacing.UP);
//			switch (this.worldObj.getBlockState(pos).getValue(BlockAlloyFurnace.FACING)) {
//				case EAST:
//					if (facing == EnumFacing.SOUTH)
//						return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, facing);
//					if (facing == EnumFacing.NORTH)
//						return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, facing);
//					break;
//				case NORTH:
//					if (facing == EnumFacing.EAST)
//						return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, facing);
//					if (facing == EnumFacing.WEST)
//						return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, facing);
//					break;
//				case SOUTH:
//					if (facing == EnumFacing.WEST)
//						return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, facing);
//					if (facing == EnumFacing.EAST)
//						return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, facing);
//					break;
//				case WEST:
//					if (facing == EnumFacing.NORTH)
//						return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, facing);
//					if (facing == EnumFacing.SOUTH)
//						return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, facing);
//					break;
//				default:
//					break;
//			}
//			return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, EnumFacing.DOWN);
			return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, facing);
		}
		return super.getCapability(capability, facing);
	}
	
	public boolean areSlotsEmpty(int[] slots) {
		for (int i = 0; i < slots.length; ++i)
			if (alloyFurnaceItemStacks[slots[i]] != null)
				return false;
		return true;
	}
	
	public ItemStack[] getCraftingStacks() {
		ItemStack[] stacks = new ItemStack[slotsCrafting.length];
		int i = 0;
		while (i < slotsCrafting.length) {
			stacks[i] = alloyFurnaceItemStacks[slotsCrafting[i]];
			i++;
		}
		return stacks;
	}
}