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

public class ItemPaintCan extends Item {
	
	public ItemPaintCan()
    {
        this.maxStackSize = 1;
        this.setMaxDamage(16);
    }

	@Override
	public ItemStack getContainerItem(ItemStack itemstack) {
		return itemstack;
	}
	
	@Override
	public boolean hasContainerItem(ItemStack itemstack) {
		return true;
	}
}
