package candy.redpowerreborn.tileentity;

import java.util.List;

import com.mojang.authlib.GameProfile;

import akka.actor.Deployer;
import candy.redpowerreborn.block.BlockAlloyFurnace;
import candy.redpowerreborn.block.BlockDeployer;
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

public class TileEntityDeployer extends TileEntityLockable implements ITickable, ISidedInventory {
	
	protected int previouslyPowered;
	
	protected ItemStack[] deployerItemStacks;
	protected String deployerCustomName;
	
	public TileEntityDeployer(){
		deployerItemStacks = new ItemStack[9];
	}
	
	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return this.deployerItemStacks.length;
	}
	
	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.deployerItemStacks[index];
	}
	
	/**
	 * Removes up to a specified number of items from an inventory slot and
	 * returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.func_188382_a(this.deployerItemStacks, index, count);
	}
	
	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.func_188383_a(this.deployerItemStacks, index);
	}
	
	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean flag = stack != null && stack.isItemEqual(this.deployerItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.deployerItemStacks[index]);
		this.deployerItemStacks[index] = stack;
		
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
			//case 0:
			//	return this.previouslyPowered;
			default:
				return 0;
		}
	}
	
	public void setField(int id, int value) {
//		switch (id) {
//			case 0:
//				this.previouslyPowered = value;
//				break;
//		}
	}
	
	@Override
	public int getFieldCount() {
		return 0;
	}
	
	@Override
	public void clear() {
		for (int i = 0; i < this.deployerItemStacks.length; ++i) {
			this.deployerItemStacks[i] = null;
		}
	}
	
	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return this.hasCustomName() ? this.deployerCustomName : "container.deployer";
	}
	
	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return this.deployerCustomName != null && !this.deployerCustomName.isEmpty();
	}
	
	public void setCustomInventoryName(String name) {
		this.deployerCustomName = name;
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerDispenser(playerInventory, this);
	}
	
	@Override
	public String getGuiID() {
		return "redpowerreborn:deployer";
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if(side == this.worldObj.getBlockState(pos).getValue(BlockDeployer.FACING)){
			int[] i = {};
			return i;
		}
		int[] i = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		return i;
	}
	
	public boolean areSlotsEmpty(int[] slots) {
		for (int i = 0; i < slots.length; ++i)
			if (this.deployerItemStacks[slots[i]] != null)
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
			if (i== index)
				return true;
		return false;
	}
	
	/**
	 * Like the old updateEntity(), except more generic.
	 */
	@Override
	public void update() {
		if (!this.worldObj.isRemote) {
			if (this.worldObj.isBlockPowered(pos)) {
				if (this.previouslyPowered == 0) {
					//TODO Find the proper delay ticks, the deployer cannot be used every other tick for sure
					this.previouslyPowered = 4;
					this.deploy();
					this.markDirty();
					BlockDeployer.setState(true, this.worldObj, this.pos);
				}
			} else {
				if (this.previouslyPowered > 0) {
					this.previouslyPowered--;
					if(this.previouslyPowered ==0)
						BlockDeployer.setState(false, this.worldObj, this.pos);
				}
			}
			
		}
	}
	
	protected void deploy() {
		// TODO set up fake player with proper inventory setup and orientation
		EnumFacing facing = worldObj.getBlockState(pos).getValue(BlockDeployer.FACING);
		
		FakePlayer placer = FakePlayerFactory.get((WorldServer) this.worldObj, new GameProfile(null, "DeployerFakePlayer"));
		// placer.interactionManager.setGameType(type);
		// TODO retract player reach to one block length
		int i = 0;
		while (i < this.deployerItemStacks.length) {
			placer.inventory.setInventorySlotContents(i, deployerItemStacks[i]);
			i++;
		}
		
		placer.setActiveHand(EnumHand.MAIN_HAND);
		placer.capabilities.isCreativeMode = false;
		placer.setPositionAndRotation(pos.offset(facing).getX() + .5, pos.offset(facing).getY() - 1, pos.offset(facing).getZ() + .5, this.getPlacerYaw(worldObj.getBlockState(pos).getValue(BlockDeployer.FACING)), this.getPlacerPitch(worldObj.getBlockState(pos).getValue(BlockDeployer.FACING)));
		
		PlaceEvent event;
		ItemStack stack;
		ActionResult<ItemStack> result;
		
		i = 0;
		f: while (i < this.deployerItemStacks.length) {
			placer.inventory.currentItem = i;
			stack = placer.inventory.getCurrentItem();
			
			if (placer.inventory.getCurrentItem() != null && placer.inventory.getCurrentItem().stackSize > 0) {
				
				event = ForgeEventFactory.onPlayerBlockPlace(placer, BlockSnapshot.getBlockSnapshot(worldObj, pos.offset(facing)), EnumFacing.UP);
				
				if (!event.isCanceled()) {
					final Item usedItem = placer.inventory.getCurrentItem().getItem();
					
					if (!worldObj.getBlockState(pos).getBlock().isReplaceable(worldObj, pos.offset(facing).down())) {
						if (usedItem.onItemUseFirst(placer.inventory.getCurrentItem(), (EntityPlayer) placer, worldObj, pos.offset(facing).down(), EnumFacing.UP, 0.5f, 0.5f, 0.5f, EnumHand.MAIN_HAND) == EnumActionResult.PASS) {
							if (Event.Result.DENY != event.getResult()) {
								// TODO perform check to see if pos.down() is replaceable like water or grass
								// if true then place block on pos not pos.down()
								
								// For when the block below the deployer is not replaceable, like grass or a machine
								if (placer.inventory.getCurrentItem().onItemUse(placer, worldObj, pos.offset(facing).down(), EnumHand.MAIN_HAND, EnumFacing.UP, 0.5f, 0.5f, 0.5f) == EnumActionResult.SUCCESS)
									break f;
									
								result = placer.inventory.getCurrentItem().useItemRightClick(worldObj, placer, EnumHand.MAIN_HAND);
								if (result.getType() == EnumActionResult.SUCCESS) {
									// placer.inventory.setInventorySlotContents(i, stack);
									// placer.inventory.addItemStackToInventory(result.getResult());
									placer.inventory.setInventorySlotContents(i, result.getResult());
									break f;
								}
							}
						}
					}
					
					if (!worldObj.getBlockState(pos).getBlock().isReplaceable(worldObj, pos.offset(facing))) {
						if (usedItem.onItemUseFirst(placer.inventory.getCurrentItem(), (EntityPlayer) placer, worldObj, pos.offset(facing), EnumFacing.UP, 0.5f, 0.5f, 0.5f, EnumHand.MAIN_HAND) == EnumActionResult.PASS) {
							if (Event.Result.DENY != event.getResult()) {
								// For when the block in front of the deployer is not replaceable, like grass or a machine
								if (placer.inventory.getCurrentItem().onItemUse(placer, worldObj, pos.offset(facing), EnumHand.MAIN_HAND, EnumFacing.UP, 0.5f, 0.5f, 0.5f) == EnumActionResult.SUCCESS)
									break f;
									
								result = placer.inventory.getCurrentItem().useItemRightClick(worldObj, placer, EnumHand.MAIN_HAND);
								if (result.getType() == EnumActionResult.SUCCESS) {
									// placer.inventory.setInventorySlotContents(i, stack);
									// placer.inventory.addItemStackToInventory(result.getResult());
									placer.inventory.setInventorySlotContents(i, result.getResult());
									break f;
								}
							}
						}
					}
					if (worldObj.getBlockState(pos).getBlock().isReplaceable(worldObj, pos.offset(facing))) {
						if (usedItem.onItemUseFirst(placer.inventory.getCurrentItem(), (EntityPlayer) placer, worldObj, pos, facing, 0.5f, 0.5f, 0.5f, EnumHand.MAIN_HAND) == EnumActionResult.PASS) {
							if (Event.Result.DENY != event.getResult()) {
								// For when the block below the deployer is replaceable, like tall grass or air AND the block in front of the deployer is replaceable
								if (placer.inventory.getCurrentItem().onItemUse(placer, worldObj, pos, EnumHand.MAIN_HAND, facing, 0.5f, 0.5f, 0.5f) == EnumActionResult.SUCCESS)
									break f;
									
								placer.setPositionAndRotation(pos.offset(facing).getX() + .5, pos.offset(facing).getY() - 1, pos.offset(facing).getZ() + .5, this.getPlacerYaw(worldObj.getBlockState(pos).getValue(BlockDeployer.FACING).getOpposite()), this.getPlacerPitch(worldObj.getBlockState(pos).getValue(BlockDeployer.FACING).getOpposite()));
								placer.setSneaking(true);
								result = placer.inventory.getCurrentItem().useItemRightClick(worldObj, placer, EnumHand.MAIN_HAND);
								if (result.getType() == EnumActionResult.SUCCESS) {
									// placer.inventory.setInventorySlotContents(i, stack);
									// placer.inventory.addItemStackToInventory(result.getResult());
									placer.inventory.setInventorySlotContents(i, result.getResult());
									break f;
								}
							}
						}
					}
				}
				
				List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.offset(facing)));
				for (Entity entity : entities) {
					if (entity.processInitialInteract(placer, placer.inventory.getCurrentItem(), EnumHand.MAIN_HAND)) {
						if (entity.applyPlayerInteraction(placer, new Vec3d(entity.posX, entity.posY, entity.posZ), placer.inventory.getCurrentItem(), EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS)
							break f;
					}
					if (entity instanceof EntityLivingBase)
						if (placer.inventory.getCurrentItem().getItem().itemInteractionForEntity(placer.inventory.getCurrentItem(), placer, (EntityLivingBase) entity, EnumHand.MAIN_HAND))
							break f;
				}
			}
			i++;
			
		}
		
		// TODO refill inventory slots for deployer
		
		ItemStack stack2;
		i = 0;
		while (i < this.deployerItemStacks.length) {
			stack2 = placer.inventory.getStackInSlot(i);
			placer.inventory.removeStackFromSlot(i);
			if (stack2 != null && stack2.stackSize <= 0)
				stack2 = null;
			this.setInventorySlotContents(i, stack2);
			i++;
		}
		
		InventoryHelper.dropInventoryItems(worldObj, pos.offset(facing), (IInventory) placer.inventory);
		
	}
	
	public float getPlacerYaw(EnumFacing facing) {
		switch (facing) {
			case DOWN:
				return 0.0f;
			case EAST:
				return -90.0f;
			case NORTH:
				return -180.0f;
			case SOUTH:
				return 0.0f;
			case UP:
				return 0.0f;
			case WEST:
				return 90.0f;
			default:
				return 90.0f;
		}
	}
	
	public float getPlacerPitch(EnumFacing facing) {
		switch (facing) {
			case DOWN:
				return 90.0f;
			case EAST:
				return 0.0f;
			case NORTH:
				return 0.0f;
			case SOUTH:
				return 0.0f;
			case UP:
				return -90.0f;
			case WEST:
				return 0.0f;
			default:
				return 90.0f;
		}
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.deployerItemStacks = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");
			
			if (j >= 0 && j < this.deployerItemStacks.length) {
				this.deployerItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
		
		if (compound.hasKey("CustomName", 8)) {
			this.deployerCustomName = compound.getString("CustomName");
		}
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < this.deployerItemStacks.length; ++i) {
			if (this.deployerItemStacks[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.deployerItemStacks[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		
		compound.setTag("Items", nbttaglist);
		
		if (this.hasCustomName()) {
			compound.setString("CustomName", this.deployerCustomName);
		}
	}
	
	public BlockPos getPos(){
		return this.pos;
	}
}
