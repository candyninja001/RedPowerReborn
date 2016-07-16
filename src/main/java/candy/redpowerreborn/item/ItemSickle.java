package candy.redpowerreborn.item;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSickle extends Item {
    protected Item.ToolMaterial theToolMaterial;
	
	public ItemSickle(Item.ToolMaterial material)
    {
		this.theToolMaterial = material;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        stack.damageItem(1, entityLiving);
        Block block = state.getBlock();
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        return isBlockHarvestableNear(blockIn);
    }

    public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
    	return isBlockHarvestableNear(state) ? this.theToolMaterial.getEfficiencyOnProperMaterial() : super.getStrVsBlock(stack, state);
    }
    
    public boolean isBlockHarvestableNear(IBlockState state){
    	Material mat = state.getMaterial();
    	if( mat == Material.GOURD || mat == Material.LEAVES || mat == Material.PLANTS || mat == Material.VINE)//|| state.getBlock() instanceof IGrowable)
    		return true;
    	return false;
    }

    public boolean isBlockHarvestableFar(IBlockState state){
    	Material mat = state.getMaterial();
    	if( mat == Material.GOURD || mat == Material.PLANTS  || mat == Material.VINE)//|| state.getBlock() instanceof IGrowable)
    		return true;
    	return false;
    }
    
    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, net.minecraft.entity.player.EntityPlayer player)
    {
        if (player.worldObj.isRemote || player.capabilities.isCreativeMode)
        {
            return false;
        }
        BlockPos pos1;
        boolean farX = true;
        boolean farY = true;
        boolean farZ = true;
        if(this.canHarvestBlock(player.worldObj.getBlockState(pos))){
        	for(int x = pos.getX() - 2; x < pos.getX() + 3; x++){
        		farX = x - pos.getX() == 2 || x - pos.getX() == -2;
        		for(int y = pos.getY() - 2; y < pos.getY() + 3; y++){
        			farY = y - pos.getY() == 2 || y - pos.getY() == -2;
        			if(y > 0 && y < player.worldObj.getActualHeight())
        			for(int z = pos.getZ() - 2; z < pos.getZ() + 3; z++){
        				farZ = z - pos.getZ() == 2 || z - pos.getZ() == -2;
        				pos1 = new BlockPos(x, y, z);
        				if(x == pos.getX() && y ==pos.getY() & z == pos.getZ()){}//skip the block selected by the player because it should be broken last with a different call
        				else if(farX || farZ || farY){
        					if(this.isBlockHarvestableFar(player.worldObj.getBlockState(pos1)))
        						player.worldObj.destroyBlock(new BlockPos(x, y, z), true);
        				}else{
        					if(this.isBlockHarvestableNear(player.worldObj.getBlockState(pos1)))
        						player.worldObj.destroyBlock(new BlockPos(x, y, z), true);
        				}
                	}
            	}
        	}
        }
        this.onBlockDestroyed(itemstack, player.worldObj, player.worldObj.getBlockState(pos), pos, player);
        return false;
    }
}
