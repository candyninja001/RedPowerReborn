package candy.redpowerreborn.block;

import candy.redpowerreborn.RedPower;
import candy.redpowerreborn.client.gui.GuiHandler;
import candy.redpowerreborn.tileentity.TileEntityAssembler;
import candy.redpowerreborn.tileentity.TileEntityDeployer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAssembler extends BlockDeployer{
	/**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityAssembler();
    }
    
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	worldIn.notifyBlockUpdate(pos, state, state, 1);
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityDeployer)
            {
            	playerIn.openGui(RedPower.instance, GuiHandler.GUI_ASSEMBLER, worldIn, pos.getX(), pos.getY(), pos.getZ());
               // playerIn.displayGUIChest((TileEntityAlloyFurnace)tileentity);
                //playerIn.addStat(StatList.furnaceInteraction);
            }

            return true;
        }
    }
}
