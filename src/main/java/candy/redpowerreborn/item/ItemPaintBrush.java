package candy.redpowerreborn.item;

import candy.redpowerreborn.tileentity.TileEntityPneumaticTube;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPaintBrush extends Item {
	public EnumDyeColor dyeColor;
	
	public ItemPaintBrush(EnumDyeColor color)
    {
        this.maxStackSize = 1;
        this.setMaxDamage(16);
        this.dyeColor = color;
    }

	@Override
	public ItemStack getContainerItem(ItemStack itemstack) {
		return itemstack;
	}
	
	@Override
	public boolean hasContainerItem(ItemStack itemstack) {
		return true;
	}
	
	/**
     * Called when a Block is right-clicked with this Item
     */
	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
        	TileEntity te = worldIn.getTileEntity(pos);
        	if(te != null && te instanceof TileEntityPneumaticTube){
        		if(((TileEntityPneumaticTube)te).applyColor(dyeColor)){
        			stack.damageItem(1, playerIn);
        			return EnumActionResult.SUCCESS;
        		}
        		
        	}
            return EnumActionResult.PASS;
        }
    }
}
