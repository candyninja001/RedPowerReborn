package candy.redpowerreborn.block;

import java.util.List;
import java.util.Random;

import candy.redpowerreborn.RedPower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBlockBreaker extends Block {
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	public static final PropertyBool ON = PropertyBool.create("on");
	
	public BlockBlockBreaker() {
		super(Material.piston);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP).withProperty(ON, false));
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		
		worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(ON, active), 3);
		worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(ON, active), 3);
	}
	
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.setDefaultFacing(worldIn, pos, state);
	}
	
	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			
			if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}
			
			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(ON, false), 2);
		}
	}
	
	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
	 * IBlockstate
	 */
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ON, false);
	}
	
	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.getFacingFromEntity(pos, placer)), 2);
	}
	
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta % 6);
		
		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(ON, false);
	}
	
	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}
	
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, ON });
	}
	
	/**
	 * Called when a neighboring block changes.
	 */
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!worldIn.isRemote) {
			if (worldIn.isBlockPowered(pos)) {
				if (!state.getValue(ON)) {
					// break the block
					//this.destroyBlock(worldIn, pos, state);
					//TODO make the break feature work
					//TODO more research on functionality, tileentity is required for when a FULL block is blocking the back of the block breaker, stacks are stored internally for dispensing later
					
					BlockDeployer.setState(true, worldIn, pos);
				}
			} else if (!worldIn.isBlockPowered(pos)) {
				if (state.getValue(ON)) {
					BlockDeployer.setState(false, worldIn, pos);
				}
			}
		}
	}
	
	protected void destroyBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.getGameRules().getBoolean("doTileDrops") && !worldIn.restoringBlockSnapshots) {
			List<ItemStack> items = worldIn.getBlockState(pos.offset(state.getValue(FACING))).getBlock().getDrops(worldIn, pos.offset(state.getValue(FACING)), worldIn.getBlockState(pos.offset(state.getValue(FACING))), 0);
			for (ItemStack item : items) {
				EntityItem entityitem;
				switch (state.getValue(FACING).getOpposite()) {
					case EAST:
						entityitem = new EntityItem(worldIn, (double) pos.getX() + 1, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, item);
						break;
					case WEST:
						entityitem = new EntityItem(worldIn, (double) pos.getX(), (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, item);
						break;
					case UP:
						entityitem = new EntityItem(worldIn, (double) pos.getX() + 0.5, (double) pos.getY() + 1, (double) pos.getZ() + 0.5, item);
						break;
					case DOWN:
						entityitem = new EntityItem(worldIn, (double) pos.getX() + 0.5, (double) pos.getY() + 0, (double) pos.getZ() + 0.5, item);
						break;
					case SOUTH:
						entityitem = new EntityItem(worldIn, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, item);
						break;
					case NORTH:
						entityitem = new EntityItem(worldIn, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, item);
						break;
					default:
						entityitem = new EntityItem(worldIn, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, item);
						break;
				}
				entityitem.setDefaultPickupDelay();
				entityitem.motionX = 0;
				entityitem.motionY = 0;
				entityitem.motionZ = 0;
				double k = 2.0;//TODO find the right value
				switch (state.getValue(FACING).getOpposite()) {
					case EAST:
						entityitem.motionX = k;
						break;
					case WEST:
						entityitem.motionX = -k;
						break;
					case UP:
						entityitem.motionY = k;
						break;
					case DOWN:
						entityitem.motionY = -k;
						break;
					case SOUTH:
						entityitem.motionZ = k;
						break;
					case NORTH:
						entityitem.motionZ = -k;
						break;
					default:
						break;
				}
				worldIn.spawnEntityInWorld(entityitem);
			}
		}
		worldIn.setBlockToAir(pos.offset(state.getValue(FACING)));
		
	}
}