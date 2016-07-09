package candy.redpowerreborn.tileentity;

import java.util.List;

import com.mojang.authlib.GameProfile;

import candy.redpowerreborn.block.BlockAlloyFurnace;
import candy.redpowerreborn.block.BlockBuffer;
import candy.redpowerreborn.inventory.ContainerBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class TileEntityBuffer extends TileEntityLockable implements ISidedInventory {
		
	protected ItemStack[] bufferItemStacks;
	protected String bufferCustomName;
	
	protected int[] allSlots = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 ,19 };
	protected int[] column1 = { 0, 1, 2, 3 };
	protected int[] column2 = { 4, 5, 6, 7 };
	protected int[] column3 = { 8, 9, 10, 11 };
	protected int[] column4 = { 12, 13, 14, 15 };
	protected int[] column5 = { 16, 17, 18, 19 };
	
	public TileEntityBuffer(){
		bufferItemStacks = new ItemStack[20];
	}
	
	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return this.bufferItemStacks.length;
	}
	
	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.bufferItemStacks[index];
	}
	
	/**
	 * Removes up to a specified number of items from an inventory slot and
	 * returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.bufferItemStacks, index, count);
	}
	
	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.bufferItemStacks, index);
	}
	
	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean flag = stack != null && stack.isItemEqual(this.bufferItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.bufferItemStacks[index]);
		this.bufferItemStacks[index] = stack;
		
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be
	 * 64, possibly will be extended.
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {}
	
	@Override
	public void closeInventory(EntityPlayer player) {}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}
	
	@Override
	public int getField(int id) {
		switch (id) {
			default:
				return 0;
		}
	}
	
	public void setField(int id, int value) {
	}
	
	@Override
	public int getFieldCount() {
		return 0;
	}
	
	@Override
	public void clear() {
		for (int i = 0; i < this.bufferItemStacks.length; ++i) {
			this.bufferItemStacks[i] = null;
		}
	}
	
	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return this.hasCustomName() ? this.bufferCustomName : "container.buffer";
	}
	
	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return this.bufferCustomName != null && !this.bufferCustomName.isEmpty();
	}
	
	public void setCustomInventoryName(String name) {
		this.bufferCustomName = name;
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerBuffer(playerInventory, this);//TODO fix this
	}
	
	@Override
	public String getGuiID() {
		return "redpowerreborn:buffer";
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		//TODO find a way to make this better
		switch(this.worldObj.getBlockState(pos).getValue(BlockBuffer.FACING)){
			case UP:
				switch(side){
					case UP:
						return this.allSlots;
					case DOWN:
						return this.column1;
					case EAST:
						return this.column4;
					case NORTH:
						return this.column3;
					case SOUTH:
						return this.column2;
					case WEST:
						return this.column5;
					default:
						int[] i = {};
						return i;
				}
			case DOWN:
				switch(side){
					case UP:
						return this.column5;
					case DOWN:
						return this.allSlots;
					case EAST:
						return this.column3;
					case NORTH:
						return this.column2;
					case SOUTH:
						return this.column1;
					case WEST:
						return this.column4;
					default:
						int[] i = {};
						return i;
				}
			case EAST:
				switch(side){
					case UP:
						return this.column2;
					case DOWN:
						return this.column3;
					case EAST:
						return this.allSlots;
					case NORTH:
						return this.column5;
					case SOUTH:
						return this.column4;
					case WEST:
						return this.column1;
					default:
						int[] i = {};
						return i;
				}
			case NORTH:
				switch(side){
					case UP:
						return this.column3;
					case DOWN:
						return this.column4;
					case EAST:
						return this.column1;
					case NORTH:
						return this.allSlots;
					case SOUTH:
						return this.column5;
					case WEST:
						return this.column2;
					default:
						int[] i = {};
						return i;
				}
			case SOUTH:
				switch(side){
					case UP:
						return this.column4;
					case DOWN:
						return this.column5;
					case EAST:
						return this.column2;
					case NORTH:
						return this.column1;
					case SOUTH:
						return this.allSlots;
					case WEST:
						return this.column3;
					default:
						int[] i = {};
						return i;
				}
			case WEST:
				switch(side){
					case UP:
						return this.column1;
					case DOWN:
						return this.column2;
					case EAST:
						return this.column5;
					case NORTH:
						return this.column4;
					case SOUTH:
						return this.column3;
					case WEST:
						return this.allSlots;
					default:
						int[] i = {};
						return i;
				}
			default:
				break;
		}
		int[] i = {};
		return i;
	}
	
	public boolean areSlotsEmpty(int[] slots) {
		for (int i = 0; i < slots.length; ++i)
			if (this.bufferItemStacks[slots[i]] != null)
				return false;
		return true;
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
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		int[] indexes = this.getSlotsForFace(direction);
		for(int i : indexes)
			if (i== index)
				return this.isItemValidForSlot(index, itemStackIn);
		return false;
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
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		int[] indexes = this.getSlotsForFace(direction);
		for(int i : indexes)
			if (i == index)
				return true;
		return false;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.bufferItemStacks = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");
			
			if (j >= 0 && j < this.bufferItemStacks.length) {
				this.bufferItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
		
		if (compound.hasKey("CustomName", 8)) {
			this.bufferCustomName = compound.getString("CustomName");
		}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		//TODO fix inventory not saving and drops not working
		compound = super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < this.bufferItemStacks.length; ++i) {
			if (this.bufferItemStacks[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.bufferItemStacks[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		
		compound.setTag("Items", nbttaglist);
		
		if (this.hasCustomName()) {
			compound.setString("CustomName", this.bufferCustomName);
		}
		return compound;
	}
	
	public BlockPos getPos(){
		return this.pos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing) {
		if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return (T) new net.minecraftforge.items.wrapper.SidedInvWrapper(this, facing);
		}
		return super.getCapability(capability, facing);
	}
}
