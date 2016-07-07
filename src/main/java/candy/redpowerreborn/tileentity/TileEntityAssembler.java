package candy.redpowerreborn.tileentity;

import java.util.List;

import com.mojang.authlib.GameProfile;

import candy.redpowerreborn.block.BlockDeployer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.Event;

public class TileEntityAssembler extends TileEntityDeployer {
	// protected ItemStack[] deployerItemStacks;// = new ItemStack[34];
	
	private int selectionMode;
	private int selectedSlot;
	private int activeSlots;
	
	public TileEntityAssembler() {
		deployerItemStacks = new ItemStack[34];
	}
	
	@Override
	public int getField(int id) {
		switch (id) {
			case 0:
				return this.selectionMode;
			case 1:
				return this.selectedSlot;
			case 2:
				return this.getActiveSlotsInt(this.getActiveSlots());// TODO find a better solution to this issue: when a stack is in a slot, the active value is not always updated accordingly, visible during drag placing items
			// return this.activeSlots;
			default:
				return 0;
		}
	}
	
	public void setField(int id, int value) {
		switch (id) {
			case 0:
				this.selectionMode = value;
				break;
			case 1:
				this.selectedSlot = value;
				break;
			case 2:
				this.activeSlots = value;
				break;
		}
	}
	
	@Override
	public int getFieldCount() {
		return 3;
	}
	
	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return this.hasCustomName() ? this.deployerCustomName : "container.assembler";
	}
	
	@Override
	public String getGuiID() {
		return "redpowerreborn:assembler";
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.selectionMode = compound.getInteger("SelectionMode");
		this.selectedSlot = compound.getInteger("SelectedSlot");
		this.activeSlots = compound.getInteger("ActiveSlots");
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("SelectionMode", this.selectionMode);
		compound.setInteger("SelectedSlot", this.selectedSlot);
		compound.setInteger("ActiveSlots", this.activeSlots);
	}
	
	public boolean isSlotActive(int index) {
		if (index == 0)
			return true;// this function is broken (i think) cannot be
		return !((this.activeSlots & Math.round(Math.pow(2, index - 1))) == 0) || this.deployerItemStacks[index] != null;
	}
	
	public boolean[] getActiveSlots() {
		boolean[] activeSlots = new boolean[16];
		for (int i = 0; i < 16; i++) {
			activeSlots[i] = isSlotActive(i);
		}
		return activeSlots;
	}
	
	public int getActiveSlotsInt(boolean[] activeSlots) {
		int number = 0;
		for (int i = 1; i < 16; i++) {
			if (activeSlots[i])
				number += Math.pow(2, i - 1);
		}
		return number;
	}
	
	public void setActiveSlots(boolean[] activeSlots) {
		int number = 0;
		for (int i = 1; i < 16; i++) {
			if (activeSlots[i])
				number += Math.pow(2, i - 1);
		}
		this.setField(2, number);
	}
	
	@Override
	public void update() {
		if (!this.worldObj.isRemote) {
			if (this.selectionMode == 0) {
				if (this.worldObj.isBlockPowered(pos)) {
					if (this.previouslyPowered == 0) {
						// TODO Find the proper delay ticks, the deployer cannot be used every other tick for sure
						this.previouslyPowered = 4;
						this.deploy();
						this.markDirty();
						BlockDeployer.setState(true, this.worldObj, this.pos);
					}
				} else {
					if (this.previouslyPowered > 0) {
						this.previouslyPowered--;
						if (this.previouslyPowered == 0)
							BlockDeployer.setState(false, this.worldObj, this.pos);
					}
				}
			}
			else if (this.selectionMode == 1) {//TODO make bundled cables work properly?
				if (this.previouslyPowered > 0) {
					this.previouslyPowered--;
					if (this.previouslyPowered == 0)
						BlockDeployer.setState(false, this.worldObj, this.pos);
				}
			}
		}
	}
	
	@Override
	protected void deploy() {
		// TODO set up fake player with proper inventory setup and orientation
		EnumFacing facing = worldObj.getBlockState(pos).getValue(BlockDeployer.FACING);
		
		FakePlayer placer = FakePlayerFactory.get((WorldServer) this.worldObj, new GameProfile(null, "AssemblerFakePlayer"));
		// placer.interactionManager.setGameType(type);
		// TODO retract player reach to one block length
		int i = 16;
		while (i < this.deployerItemStacks.length) {
			placer.inventory.setInventorySlotContents(i - 16, deployerItemStacks[i]);
			i++;
		}
		
		placer.setActiveHand(EnumHand.MAIN_HAND);
		placer.capabilities.isCreativeMode = false;
		placer.setPositionAndRotation(pos.offset(facing).getX() + .5, pos.offset(facing).getY() - 1, pos.offset(facing).getZ() + .5, this.getPlacerYaw(worldObj.getBlockState(pos).getValue(BlockDeployer.FACING)), this.getPlacerPitch(worldObj.getBlockState(pos).getValue(BlockDeployer.FACING)));
		
		PlaceEvent event;
		ItemStack stack;
		ActionResult<ItemStack> result;
		
		ItemStack selector = this.deployerItemStacks[this.selectedSlot];
		
		i = 16; // start at 16 to skip the selector slots
		f: while (i < this.deployerItemStacks.length) {
			if (i == 25) {// perform a switch of inventory slots between hotbar and inventory to correctly handle some tasks, gets reversed later.
				ItemStack hotbarStack;
				ItemStack inventoryStack;
				
				int f = 0;
				while (f < 9) {
					hotbarStack = placer.inventory.getStackInSlot(f);
					inventoryStack = placer.inventory.getStackInSlot(f + 9);
					placer.inventory.setInventorySlotContents(f, inventoryStack);
					placer.inventory.setInventorySlotContents(f + 9, hotbarStack);
					f++;
				}
			}
			placer.inventory.currentItem = (i - 16) % 9;
			stack = placer.inventory.getCurrentItem();
			
			if (placer.inventory.getCurrentItem() != null && placer.inventory.getCurrentItem().stackSize > 0 && placer.inventory.getCurrentItem().func_185136_b(selector)) {
				
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
		if (i >= 25) {
			ItemStack hotbarStack;
			ItemStack inventoryStack;
			
			int f = 0;
			while (f < 9) {
				hotbarStack = placer.inventory.getStackInSlot(f);
				inventoryStack = placer.inventory.getStackInSlot(f + 9);
				placer.inventory.setInventorySlotContents(f, inventoryStack);
				placer.inventory.setInventorySlotContents(f + 9, hotbarStack);
				f++;
			}
		}
		// TODO refill inventory slots for assembler
		
		ItemStack stack2;
		i = 16;
		while (i < this.deployerItemStacks.length) {
			stack2 = placer.inventory.getStackInSlot(i - 16);
			placer.inventory.removeStackFromSlot(i - 16);
			if (stack2 != null && stack2.stackSize <= 0)
				stack2 = null;
			this.setInventorySlotContents(i, stack2);
			i++;
		}
		
		InventoryHelper.dropInventoryItems(worldObj, pos.offset(facing), (IInventory) placer.inventory);
		
		// Advance selectedSlot to the next valid value
		boolean[] activeSlots = this.getActiveSlots();
		this.selectedSlot++;
		if (this.selectedSlot > 15)
			this.selectedSlot = 0;
		while (!activeSlots[this.selectedSlot]) {
			this.selectedSlot++;
			if (this.selectedSlot > 15)
				this.selectedSlot = 0;
		}
	}
}
