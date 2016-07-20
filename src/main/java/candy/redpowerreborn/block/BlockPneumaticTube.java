package candy.redpowerreborn.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.IExtendedBlockState;

public class BlockPneumaticTube extends Block {

	protected static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.25D, 0.75D, 0.75D, 0.75D);
	
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	
	public BlockPneumaticTube(Material materialIn) {
		super(materialIn);
	}

	/**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return DEFAULT_AABB;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess access, BlockPos pos) {
        state = state.withProperty(UP, access.getBlockState(pos.up()).getBlock() == this);
        state = state.withProperty(DOWN, access.getBlockState(pos.down()).getBlock() == this);
        state = state.withProperty(NORTH, access.getBlockState(pos.north()).getBlock() == this);
        state = state.withProperty(EAST, access.getBlockState(pos.east()).getBlock() == this);
        state = state.withProperty(SOUTH, access.getBlockState(pos.south()).getBlock() == this);
        state = state.withProperty(WEST, access.getBlockState(pos.west()).getBlock() == this);

        return state;
    }

//    @Override
//    public IExtendedBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
//        IExtendedBlockState extended = (IExtendedBlockState) super.getActualState(state, world, pos);
//        
//        extended = extended.withProperty(UP, world.getBlockState(pos.up()).getBlock() == this);
//        extended = extended.withProperty(DOWN, world.getBlockState(pos.down()).getBlock() == this);
//        extended = extended.withProperty(NORTH, world.getBlockState(pos.north()).getBlock() == this);
//        extended = extended.withProperty(EAST, world.getBlockState(pos.east()).getBlock() == this);
//        extended = extended.withProperty(SOUTH, world.getBlockState(pos.south()).getBlock() == this);
//        extended = extended.withProperty(WEST, world.getBlockState(pos.west()).getBlock() == this);
//        
//        return extended;
//    }
    
    public int getMetaFromState(IBlockState state)
    {
    	return 0;
    }
    
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {UP, DOWN, NORTH, EAST, WEST, SOUTH});
    }
}
