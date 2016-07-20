package candy.redpowerreborn.block;

import java.util.Random;

import javax.annotation.Nullable;

import candy.redpowerreborn.item.RedPowerItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFlax extends BlockCrops {
	private static final AxisAlignedBB[] FLAX_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.09375D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.09375D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.34375D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.34375D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.59375D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.84375D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.84375D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };
	public static final PropertyEnum<BlockDoublePlant.EnumBlockHalf> HALF = PropertyEnum.<BlockDoublePlant
			.EnumBlockHalf> create("half", BlockDoublePlant.EnumBlockHalf.class);
	
	protected BlockFlax()
    {
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(HALF, BlockDoublePlant.EnumBlockHalf.LOWER));
        this.setTickRandomly(true);
        this.setCreativeTab((CreativeTabs)null);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.disableStats();
    }

	protected Item getSeed() {
		return RedPowerItems.FLAX_SEED;
	}

	protected Item getCrop() {
		return Items.STRING;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FLAX_AABB[((Integer) state.getValue(this.getAgeProperty())).intValue()];
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AGE, HALF });
	}

	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.canBlockStay(worldIn, pos, state)) {
			boolean flag = state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER;
			BlockPos blockpos = flag ? pos : pos.up();
			BlockPos blockpos1 = flag ? pos.down() : pos;
			Block block = (Block) (flag ? this : worldIn.getBlockState(blockpos).getBlock());
			Block block1 = (Block) (flag ? worldIn.getBlockState(blockpos1).getBlock() : this);

			if (!flag)
				this.dropBlockAsItem(worldIn, pos, state, 0); // Forge move
																// above the
																// setting to
																// air.

			if (block == this) {
				worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
			}

			if (block1 == this) {
				worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
			}
		}
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER) {
			return worldIn.getBlockState(pos.down()).getBlock() == this;
		} else {
			return super.canBlockStay(worldIn, pos, state);//TODO this is broken
		}
	}

	public IBlockState withAge(int age) {
		if (age > 7)
			return this.getDefaultState().withProperty(AGE, 7).withProperty(this.HALF,
					BlockDoublePlant.EnumBlockHalf.UPPER);
		return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age)).withProperty(HALF,
				BlockDoublePlant.EnumBlockHalf.LOWER);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.withAge(meta);
	}
	
	/**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
    	if(state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER)
    		return 8;
        return this.getAge(state);
    }

	@Override
	public void grow(World worldIn, BlockPos pos, IBlockState state) {
		int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
		int j = 7;

		if (i > j) {
			i = j;
		}

		if (state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.LOWER) {
			if (i == j) {
				if (worldIn.isAirBlock(pos.up())) {
					worldIn.setBlockState(pos.up(), this.withAge(i + 1), 2);
					worldIn.setBlockState(pos.up(), this.withAge(i + 1), 2);
					worldIn.setBlockState(pos, this.withAge(i), 2);
					worldIn.setBlockState(pos, this.withAge(i), 2);
				} else {
					worldIn.setBlockState(pos, this.withAge(i - 1), 2);
					worldIn.setBlockState(pos, this.withAge(i - 1), 2);
				}
				return;
			}
			worldIn.setBlockState(pos, this.withAge(i), 2);
			worldIn.setBlockState(pos, this.withAge(i), 2);
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		this.checkAndDropBlock(worldIn, pos, state);

		if (state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.LOWER) {
			if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
				int i = this.getAge(state);

				if (i < this.getMaxAge()) {
					float f = getGrowthChance(this, worldIn, pos);

					if (rand.nextInt((int) (25.0F / f) + 1) == 0) {
						if (i == 6) {
							if (worldIn.isAirBlock(pos.up())) {
								worldIn.setBlockState(pos.up(), this.withAge(i + 2), 2);
								worldIn.setBlockState(pos, this.withAge(i + 1), 2);
							}
						} else {
							worldIn.setBlockState(pos, this.withAge(i + 1), 2);
						}
					}
				}
			}
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER) {
			return null;
		} else {
			return super.getItemDropped(state, rand, fortune);
		}
	}

	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (state.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER) {
			if (worldIn.getBlockState(pos.down()).getBlock() == this) {
				if (!player.capabilities.isCreativeMode) {
					if (!worldIn.isRemote) {
						worldIn.destroyBlock(pos.down(), true);
					} else {
						worldIn.setBlockToAir(pos.down());
					}
				} else {
					worldIn.setBlockToAir(pos.down());
				}
			}
		} else if (worldIn.getBlockState(pos.up()).getBlock() == this) {
			worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	/**
	 * Whether this IGrowable can grow
	 */
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		if (state.getValue(AGE) == 6) {
			return worldIn.isAirBlock(pos.up());
		}
		if (state.getValue(AGE) == 7) {
			return false;
		}
		return true;
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if (state.getValue(AGE) == 6) {
			return worldIn.isAirBlock(pos.up());
		}
		if (state.getValue(AGE) == 7) {
			return false;
		}
		return true;
	}

	// TODO find out what this is for
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.grow(worldIn, pos, state);
	}
}