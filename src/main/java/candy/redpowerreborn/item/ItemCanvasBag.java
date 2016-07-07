package candy.redpowerreborn.item;

import candy.redpowerreborn.RedPower;
import candy.redpowerreborn.client.gui.GuiHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCanvasBag extends Item {
	public ItemCanvasBag(){
		super();
		setMaxStackSize(1);
		this.addPropertyOverride(new ResourceLocation("color"), new IItemPropertyGetter()
        {
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn)
            {
                return ItemCanvasBag.getColorFromStack(stack).getMetadata() / 16.0f;
            }
        });
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}
	
	/**
	 * get the EnumDyeColor for the bag, can return white if there is no color
	 */
	public static EnumDyeColor getColorFromStack(ItemStack stack){
		if(!(stack.getItem() instanceof ItemCanvasBag) || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("Color"))
			return EnumDyeColor.WHITE;
		//stack.getTagCompound().getTagList("Color", Constants.NBT.TAG_INT);
		return EnumDyeColor.byMetadata(stack.getTagCompound().getInteger("Color"));
	}
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world, EntityPlayer player, EnumHand hand){
    	
    	if (!world.isRemote)
		{
			// If player not sneaking, open the inventory gui
			if (!player.isSneaking()) {
				if(hand.equals(EnumHand.MAIN_HAND))
					player.openGui(RedPower.instance, GuiHandler.GUI_CANVASBAG, world, 0, 0, 0);
				if(hand.equals(EnumHand.OFF_HAND))
					player.openGui(RedPower.instance, GuiHandler.GUI_CANVASBAG_OFF, world, 0, 0, 0);
			}
		}
    	
    	return ActionResult.newResult(EnumActionResult.PASS, itemstack);
    }
}
