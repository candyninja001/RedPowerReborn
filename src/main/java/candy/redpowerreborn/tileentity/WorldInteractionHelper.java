package candy.redpowerreborn.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldInteractionHelper {
	// This class provides RedPower machines access to simple functions like:
	// dispensing a stack based on orientation into the world, an inventory, or pneumatic tube
	// taking in stacks from the world, and blocks
	// possibly calling for stacks connected to the pneumatic tube system
	// etc
	// TODO update this list as features are added and add details descriptions to each function explaining all actions and prerequisites
	
	/**
	 * This function will attempt to dispense an ItemStack into the world from a specific BlockPos
	 * in a World. It will check to see if the block in front of the TilEntity is air (or grass)
	 * an inventory or Pneumatic tube. The function will attempt to do the following tasks in this
	 * order: Send the ItemStack into the Pneumatic Tube, send the ItemStack into the inventory,
	 * send the ItemStack into the World as an EntityItem.
	 * @param stack, the ItemStack to be dispensed
	 * @param worldObj, the world
	 * @param pos, the BlockPos in the world
	 * @param facing, the side to dispense the stack from
	 * @param coloring, the color to be used if the ItemStack is put into a pneumatic tube system
	 * @return The remaining ItemStack, null if it worked properly.
	 */
	public static ItemStack dispenseItem(ItemStack stack, World worldObj, BlockPos pos, EnumFacing facing, EnumDyeColor coloring){
		BlockPos posDispense = pos.offset(facing);
		if(worldObj.isAirBlock(posDispense)){
			double x = pos.getX() + 0.5;
			double y = pos.getY() + 0.5;
			double z = pos.getZ() + 0.5;
			double motionX = 0;
			double motionY = 0;
			double motionZ = 0;
			double speed = 0.5;
			switch(facing){
			case DOWN:
				y-=0.5;
				motionY-=speed;
				break;
			case EAST:
				x+=0.5;
				motionX+=speed;
				break;
			case NORTH:
				z-=0.5;
				motionZ-=speed;
				break;
			case SOUTH:
				z+=0.5;
				motionZ+=speed;
				break;
			case UP:
				y+=0.5;
				motionY+=speed;
				break;
			case WEST:
				x-=0.5;
				motionX-=speed;
				break;
			}
			EntityItem entityitem = new EntityItem(worldObj, x, y, z, stack);
	        entityitem.motionX = motionX;
	        entityitem.motionY = motionY;
	        entityitem.motionZ = motionZ;
	        entityitem.setDefaultPickupDelay();
	        worldObj.spawnEntityInWorld(entityitem);
		}
		return stack;
	}
}
